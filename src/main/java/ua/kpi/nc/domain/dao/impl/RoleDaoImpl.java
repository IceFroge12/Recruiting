package ua.kpi.nc.domain.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.kpi.nc.domain.dao.RoleDao;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.proxy.UserProxy;
import ua.kpi.nc.domain.model.impl.real.RoleImpl;

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
public class RoleDaoImpl extends DaoSupport implements RoleDao {

    private static Logger log = Logger.getLogger(RoleDaoImpl.class.getName());

    public RoleDaoImpl() {
    }

    @Override
    public Role getByID(Long id) {
        String sql = "SELECT * FROM \"role\" WHERE \"role\".id = " + id;
        log.trace("Looking for role with id = " + id);
        Role role = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result set");
            if (resultSet.next()) {
                log.trace("Create role to return");
                role = new RoleImpl(
                        resultSet.getLong("id"),
                        resultSet.getString("role"),
                        getUsersByID(resultSet.getLong("id"))
                        );

            }
        } catch (SQLException e) {
            log.error("Cannot read user", e);
            }
        if (null == role) {
            log.debug("User not found");
        } else {
            log.trace("User " + role + " found");
        }
        log.trace("Returning user");
        return role;
    }

    private Set<User> getUsersByID(Long roleId){
        Set<User> users = new HashSet<>();
        String sql = "SELECT id_user FROM user_role WHERE id_role = " + roleId;
        log.trace("Looking users for role with roleId = " + roleId);
        User tempUser;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result set");
            while (resultSet.next()) {
                log.trace("Create users to add to the set");
                tempUser = new UserProxy(resultSet.getLong("id_user"));
                users.add(tempUser);
                log.trace("User " + tempUser.getId() + " added to set");
            }
        } catch (SQLException e) {
            log.error("Cannot read user", e);
            }
        return users;
    }

}
