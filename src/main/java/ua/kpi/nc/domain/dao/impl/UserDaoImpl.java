package ua.kpi.nc.domain.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ua.kpi.nc.domain.dao.DaoException;
import ua.kpi.nc.domain.dao.UserDao;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.proxy.RoleProxy;
import ua.kpi.nc.domain.model.impl.real.UserImpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by Chalienko on 13.04.2016.
 */
@Component
public class UserDaoImpl extends DaoSupport implements UserDao {

    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    public UserDaoImpl() {
    }

    @Override
    public User getByID(Long id) throws DaoException {
        String sql = "SELECT * FROM \"user\" WHERE \"user\".id = " + "'" + id + "'";
        log.trace("Looking for user with id = " + id);
        return getByQuery(sql);
    }


    @Override
    public User getByUsername(String username) throws DaoException {
        String sql = "SELECT * FROM \"user\" WHERE \"user\".username = " + "'" + username + "'";
        log.trace("Looking for user with username = " + username);
        return getByQuery(sql);
    }

    @Override
    public boolean isExist(String username) throws DaoException {
        String sql ="select exists(SELECT username from \"user\" where username =" +  "'"
                + username + "')";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result");
            if (resultSet.next()) {
               return resultSet.getBoolean("exists");
            }
        }
        catch (SQLException e) {
            log.error("Cannot read user" + e);
            throw new DaoException("Cannot read user", e);
        }
        return false;
    }

    @Override
    public void insertUser(User user, Role role) throws DaoException {

    }

    @Override
    public void deleteUser(User user) throws DaoException {

    }


    private User getByQuery(String sql) throws DaoException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result set");
            if (resultSet.next()) {
                log.trace("Create user to return");
                user = new UserImpl(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        getRolesByUserId(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            log.error("Cannot read user" + e);
            throw new DaoException("Cannot read user", e);
        }
        if (null == user) {
            log.debug("User not found");
        } else {
            log.trace("User " + user + " found");
        }
        log.trace("Returning user");
        return user;
    }

    private Set<Role> getRolesByUserId(Long userId) throws DaoException {
        Set<Role> roles = new HashSet<>();
        String sql = "SELECT id_role FROM user_role WHERE id_user = " + userId;
        log.trace("Looking roles for user with userId = " + userId);
        Role tempRole;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result set");
            while (resultSet.next()) {
                log.trace("Create roles to add to the set");
                tempRole = new RoleProxy(resultSet.getLong("id_role"));
                roles.add(tempRole);
                log.trace("Roles " + tempRole.getId() + " added to set");
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot read role", e);
        }
        return roles;
    }
}
