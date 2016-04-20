package ua.kpi.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.kpi.nc.persistence.dao.UserDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.UserService;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Repository
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User getUserByID(Long id) {
        return userDao.getByID(id);
    }

    @Override
    public boolean isExist(String username) {
        return userDao.isExist(username);
    }

    @Override
    public int insertUser(User user, Role role) {
        return userDao.insertUser(user,role);
    }

    @Override
    public int addRole(User user, Role role) {
        return userDao.addRole(user, role);
    }

    @Override
    public int deleteRole(User user, Role role) {
        return userDao.deleteRole(user, role);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUser(user);
    }
}
