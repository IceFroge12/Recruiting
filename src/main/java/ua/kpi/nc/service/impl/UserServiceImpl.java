package ua.kpi.nc.service.impl;

import org.springframework.stereotype.Service;
import ua.kpi.nc.model.User;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 13.04.16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByEmail() {
        return null;
        //TODO
    }

    @Override
    public String getRoleByUserId(Long id) {
        return null;
        //TODO

    }
}
