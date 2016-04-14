package ua.kpi.nc.domain.model.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.kpi.nc.config.AppConfig;
import ua.kpi.nc.config.DataConfig;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.UserImpl;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.impl.RoleServiceImpl;
import ua.kpi.nc.service.impl.UserServiceImpl;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

public class UserProxy implements User {

    @Autowired
    private UserService userService;

    private Long id;
    private UserImpl user;

    public UserProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getLastName() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        if (user == null) {
            user = downloadUser();
        }
        user.setUsername(lastName);
    }

    @Override
    public String getUsername() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getLastName();
    }

    @Override
    public void setUsername(String username) {
        if (user == null) {
            user = downloadUser();
        }
        user.setUsername(username);
    }

    @Override
    public String getPassword() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getPassword();
    }

    @Override
    public void setPassword(String password) {
        if (user == null) {
            user = downloadUser();
        }
        user.setPassword(password);
    }

    @Override
    public String getFirstName() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        if (user == null) {
            user = downloadUser();
        }
        user.setFirstName(firstName);
    }

    @Override
    public Set<Role> getRoles() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getRoles();
    }

    @Override
    public void setRoles(Set<Role> roles) {
        if (user == null) {
            user = downloadUser();
        }
        user.setRoles(roles);
    }

    private UserImpl downloadUser() {
        return (UserImpl) userService.getUserByID(id);
    }

}
