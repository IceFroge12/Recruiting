package ua.kpi.nc.controller.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 13.04.16.
 */
public class UserAuthService implements UserDetailsService {

    private UserService userService;

    private UserAuthService(){
        userService = ServiceFactory.getUserService();
    }

    private static class UserAuthServiceHolder{
        private static final UserAuthService HOLDER = new UserAuthService();
    }

    public static UserAuthService getInstance(){
        return UserAuthServiceHolder.HOLDER;
    }


    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userService.getUserByUsername(userName);
        if (user == null) {
            return null;
        }
        return user;
    }
}

