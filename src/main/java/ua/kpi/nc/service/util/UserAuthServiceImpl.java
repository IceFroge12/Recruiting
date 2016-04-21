package ua.kpi.nc.service.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 13.04.16.
 */
public class UserAuthServiceImpl implements UserDetailsService {

    private static UserDetailsService userDetailsService;

    private UserAuthServiceImpl() {
        userService = ServiceFactory.getUserService();
    }

    public static UserDetailsService getInstance(){
        if(userDetailsService==null){
            userDetailsService = new UserAuthServiceImpl();
        }
        return userDetailsService;
    }

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        System.out.println("USERNAMEE"+userName);

        User user = userService.getUserByUsername(userName);
        for (Role role : user.getRoles()) {
            System.out.println(role);
        }
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));

    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            System.out.println(role.getRoleName());
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

}

