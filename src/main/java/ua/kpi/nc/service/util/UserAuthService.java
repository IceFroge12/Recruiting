package ua.kpi.nc.service.util;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 13.04.16.
 */
public class UserAuthService implements UserDetailsService {

    private static UserAuthService userAuthService;
    private UserService userService;

    private UserAuthService() {
        userService = ServiceFactory.getUserService();
    }

    public static UserAuthService getInstance() {
        if (userAuthService == null) {
            userAuthService = new UserAuthService();
        }
        return userAuthService;
    }

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userService.getUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user;

    }

}

