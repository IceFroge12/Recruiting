package ua.kpi.nc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.UserService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 13.04.16.
 */
@Service("userAuthService")
public class UserAuthServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    private java.util.logging.Logger log = java.util.logging.Logger.getLogger(String.valueOf(UserAuthServiceImpl.class));


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

           User user = userService.getUserByUsername(email);

            if(user==null){
                throw new UsernameNotFoundException("Username not found");
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(Role role: user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }

}

