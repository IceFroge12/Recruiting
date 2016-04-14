package ua.kpi.nc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.dao.DaoException;
import ua.kpi.nc.domain.dao.RoleDao;
import ua.kpi.nc.domain.dao.impl.RoleDaoImpl;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.service.RoleService;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    private static Logger log = Logger.getLogger(RoleServiceImpl.class.getName());

    @Override
    public Role getRoleById(Long id) {
        try {
            return roleDao.getByID(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Cannot get role with id: " + id, e);
        }
        return null;
    }
}
