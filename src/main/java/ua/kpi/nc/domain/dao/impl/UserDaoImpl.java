package ua.kpi.nc.domain.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.kpi.nc.domain.dao.UserDao;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.SocialInformation;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.proxy.RoleProxy;
import ua.kpi.nc.domain.model.impl.proxy.SocialInformationProxy;
import ua.kpi.nc.domain.model.impl.real.UserImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Component("userDao")
public class UserDaoImpl extends DaoSupport implements UserDao {

    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    public UserDaoImpl() {
    }

    @Override
    public User getByID(Long id) {
        String sql = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, " +
                "u.password, ur.id_role, si.id AS id_social\n" +
                "FROM \"user\" u\n" +
                "  INNER JOIN user_role ur ON id = id_user\n" +
                "  LEFT JOIN social_information si ON u.id = si.id_user\n" +
                "WHERE u.id =" + id;
        log.trace("Looking for user with id = " + id);
        return getUserByQuery(sql);
    }

    @Override
    public User getByUsername(String email) {
        String sql = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, " +
                "u.password, ur.id_role, si.id AS id_social\n" +
                "FROM \"user\" u\n" +
                "  INNER JOIN user_role ur ON id = id_user\n" +
                "  LEFT JOIN social_information si ON u.id = si.id_user\n" +
                "WHERE u.email =" + "'" + email + "'";
        log.trace("Looking for user with email = " + email);
        return getUserByQuery(sql);
    }

    private User getUserByQuery(String sql) {
        User user = new UserImpl();
        Set<Role> roles = new HashSet<>();
        Set<SocialInformation> socialInformations = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result set");
            if (resultSet.next()) {
                log.trace("Create user to return");
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setSecondName(resultSet.getString("second_name"));
                user.setPassword(resultSet.getString("password"));
                roles.add(new RoleProxy(resultSet.getLong("id_role")));
                socialInformations.add(new SocialInformationProxy(resultSet.getLong("id_social")));
            }
            while (resultSet.next()) {
                roles.add(new RoleProxy(resultSet.getLong("id_role")));
                socialInformations.add(new SocialInformationProxy(resultSet.getLong("id_social")));
            }
            user.setRoles(roles);
            user.setSocialInformations(socialInformations);
        } catch (SQLException e) {
            log.error("Cannot read user", e);
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

    @Override
    public boolean isExist(String email) {
        String sql = "select exists(SELECT email from \"user\" where email =" + "'"
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
        } catch (SQLException e) {
            log.error("Cannot read user" + e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
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
    public boolean insertUser(User user, Role role) {
        String sqlUser = "INSERT INTO \"user\"(email, first_name, second_name, last_name,password) VALUES (?,?,?,?,?)";
        String sql = "INSERT INTO \"user_role\"(id_user, id_role) VALUES (?,?)";
        try (Connection connection = dataSource.getConnection()) {
            log.trace("Open connection");
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sqlUser,Statement.RETURN_GENERATED_KEYS) ) {
                log.trace("Create prepared statement for user");
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getFirstName());
                statement.setString(3, user.getSecondName());
                statement.setString(4, user.getLastName());
                statement.setString(5, user.getPassword());
                System.out.println("Create generatedKeys");
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    user.setId(resultSet.getLong(1));
                } else {
                    return false;
                }
            } catch (SQLException eStatement) {
                log.error("Cannot insert user: " + user.getEmail(), eStatement);
                return false;
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setLong(1,user.getId() );
                statement.setLong(2, role.getId());
                statement.executeUpdate();
            } catch (SQLException eStatement) {
                log.error("Cannot insert role:" + role.getRoleName() + " User: " + user.getEmail(), eStatement);
                return false;
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            log.error("Cannot insert user", e);
            return false;
        }
    }

    @Override
    public boolean addRole(User user, Role role) {
        if (user.getId() == null) {
            log.warn("User: "+ user.getEmail() + " don`t have id");
            return false;
        }
        String sql = "INSERT INTO \"user_role\"(id_user, id_role) VALUES (?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            statement.setLong(1, user.getId());
            statement.setLong(2, role.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("Cannot insert role", e);
            return false;
        }
    }

    @Override
    public boolean deleteRole(User user, Role role) {
        if (user.getId() == null) {
            log.warn("User: "+ user.getEmail() + " don`t have id");
            return false;
        }
        String sql = "DELETE FROM \"user_role\" WHERE id_user= " + user.getId() + " AND"
                + "id_role = " + role.getId();
        log.trace("Delete role: " + role.getRoleName() + ", User: " + user.getEmail());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("Cannot delete role" + e);
            return false;
        }
    }

}
