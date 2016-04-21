package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface UserDao {

    User getByUsername(String username);

    User getByID(Long id);

    boolean isExist(String username);

    int deleteUser(User user);

    Long insertUser(User user, Connection connection);

    boolean addRole(User user, Role role);

    boolean addRole(User user, Role role, Connection connection);

    int deleteRole(User user, Role role);

    Set<User> getAll();
}
