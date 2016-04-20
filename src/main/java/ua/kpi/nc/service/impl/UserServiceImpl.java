package ua.kpi.nc.service.impl;

import org.springframework.stereotype.Repository;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.UserDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

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
    public boolean insertUser(User user, Role role) {
        try(Connection connection = DataSourceFactory.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedUserId = userDao.insertUser(user, connection);
            user.setId(generatedUserId);
            userDao.addRole(user,role,connection);
            connection.commit();
        } catch (SQLException e) {
            //logg
            return false;
        }
        return true;
    }

    @Override
    public boolean addRole(User user, Role role) {
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
