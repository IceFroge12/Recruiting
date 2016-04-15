package ua.kpi.nc.domain.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
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
    public User getByID(Long id){
        String sql = "SELECT * FROM \"user\" WHERE \"user\".id = " + "'" + id + "'";
        log.trace("Looking for user with id = " + id);
        return getUserByQuery(sql);
    }

    @Override
    public User getByUsername(String email){
        String sql = "SELECT * FROM \"user\" WHERE \"user\".email = " + "'" + email + "'";
        log.trace("Looking for user with email = " + email);
        return getUserByQuery(sql);
    }

    @Override
    public boolean isExist(String email){
        String sql ="select exists(SELECT email from \"user\" where email =" +  "'"
                + email + "')";
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
        }
        return false;
    }

    @Override
    public boolean insertUser(User user){
        String sql = "INSERT INTO \"user\"(email, first_name, second_name, last_name) VALUES (?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            statement.setString(1,user.getEmail());
            statement.setString(2,user.getFirstName());
            statement.setString(3,user.getSecondName());
            statement.setString(4,user.getLastName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("Cannot insert user", e);
            return false;
        }
    }

    //TODO
    //Delete not by PK?
    @Override
    public boolean deleteUser(User user){
        String sql = "DELETE FROM \"user\" WHERE email=" + "'" + user.getEmail() + "'";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("Cannot delete user" + e);
            return false;
        }
    }

    @Override
    public boolean addRole(User user, Role role){
        return false;
    }

    @Override
    public boolean deleteRole(User user, Role role) {
        return false;
    }

    private User getUserByQuery(String sql){
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
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second"),
                        resultSet.getString("last_name"),
                        getRolesByUserId(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            log.error("Cannot read user" + e);
            return null;
        }
        if (null == user) {
            log.debug("User not found");
        } else {
            log.trace("User " + user + " found");
        }
        log.trace("Returning user");
        return user;
    }

    private Set<Role> getRolesByUserId(Long userId){
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
            log.error("Cannot read role", e);
            return null;
        }
        return roles;
    }
}
