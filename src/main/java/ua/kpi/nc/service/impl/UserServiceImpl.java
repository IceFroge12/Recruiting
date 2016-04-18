package ua.kpi.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.kpi.nc.domain.dao.UserDao;
import ua.kpi.nc.domain.dao.impl.UserDaoImpl;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.UserService;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
    public boolean insertUser(User user, Role role) {
        return userDao.insertUser(user,role);
    }

    @Override
    public boolean addRole(User user, Role role) {
        return userDao.addRole(user, role);
    }

    @Override
    public boolean deleteRole(User user, Role role) {
        return userDao.deleteRole(user, role);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public boolean deleteUserByToken(String token) {
        return false;
    }

    @Override
    public boolean getUserByToken(String token) {
        return false;
    }

    @Override
    public void confirmUser(String token) {

    }
}
