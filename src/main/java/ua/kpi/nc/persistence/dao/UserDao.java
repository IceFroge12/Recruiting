package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface UserDao {

    User getByUsername(String username);

    User getByID(Long id);

    boolean isExist(String username);

    int insertUser(User user);

    int deleteUser(User user);

    int addRole(User user, Role role);

    int deleteRole(User user, Role role);
}
