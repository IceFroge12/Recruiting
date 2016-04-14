package ua.kpi.nc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.kpi.nc.domain.dao.DaoException;
import ua.kpi.nc.domain.dao.UserDao;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.UserService;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User getUserByUsername(String username) {
        try {
            return userDao.getByUsername(username);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Cannot get user with username: " + username, e);
        }
        return null;
    }

    @Override
    public User getUserByID(Long id) {
        try {
            return userDao.getByID(id);
        } catch (DaoException e) {
            e.printStackTrace();
            log.error("Cannot get user with id: " + id, e);
        }
        return null;
    }

    @Override
    public boolean isExist(String username) {
        try {
            return userDao.isExist(username);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return false;
    }
}
