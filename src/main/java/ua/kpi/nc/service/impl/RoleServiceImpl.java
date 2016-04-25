package ua.kpi.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.kpi.nc.persistence.dao.RoleDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.service.RoleService;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.getByID(id);
    }

    @Override
    public Role getRoleByTitle(String title) {
        return roleDao.getByTitle(title);
    }

    @Override
    public Set<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Long insertRole(Role role) {
        return roleDao.insertRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public int deleteRole(Role role) {
        return roleDao.deleteRole(role);
    }
}
