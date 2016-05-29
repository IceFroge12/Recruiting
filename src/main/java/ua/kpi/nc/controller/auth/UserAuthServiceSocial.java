package ua.kpi.nc.controller.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * Created by IO on 29.05.2016.
 */
public class UserAuthServiceSocial implements UserDetailsService {

    private UserService userService;

    private UserAuthServiceSocial() {
        userService = ServiceFactory.getUserService();
    }

    private static class UserAuthServiceSocialHolder{
        private static final UserAuthServiceSocial HOLDER = new UserAuthServiceSocial();
    }

    public static UserAuthServiceSocial getInstance(){
        return UserAuthServiceSocialHolder.HOLDER;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
