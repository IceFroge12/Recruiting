package ua.kpi.nc.service;

import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;

/**
 * Created by Chalienko on 13.04.2016.
 */

public interface UserService {
    User getUserByUsername(String username);

    User getUserByID(Long id);

    boolean isExist(String username);

    void insertUser(User user, Role role);

    void addRole(User user, Role role);

    void deleteRole(User user, Role role);
}