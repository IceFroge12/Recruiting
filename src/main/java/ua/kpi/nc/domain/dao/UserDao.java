package ua.kpi.nc.domain.dao;

import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface UserDao {

    User getByUsername(String username);

    User getByID(Long id);

    boolean isExist(String username);

    boolean insertUser(User user);

    boolean deleteUser(User user);

    boolean addRole(User user, Role role);

    boolean deleteRole(User user, Role role);
}
