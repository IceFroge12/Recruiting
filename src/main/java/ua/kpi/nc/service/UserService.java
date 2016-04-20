package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 13.04.2016.
 */

public interface UserService {
    User getUserByUsername(String username);

    User getUserByID(Long id);

    boolean isExist(String username);

    boolean insertUser(User user, Role role);

    boolean addRole(User user, Role role);

    int deleteRole(User user, Role role);

    int deleteUser(User user);
}