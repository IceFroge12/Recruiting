package ua.kpi.nc.service;

import ua.kpi.nc.model.User;

/**
 * Created by dima on 13.04.16.
 */
public interface UserService {

    public User getUserByEmail();

    public String getRoleByUserId(Long id);

}
