package ua.kpi.nc.domain.model.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.UserImpl;
import ua.kpi.nc.service.UserService;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Configurable
public class UserProxy implements User {

    private Long id;

    private UserImpl user;

    @Autowired
    private UserService userService;

    public UserProxy() {
    }

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

    @Override
    public String getToken() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getToken();
    }

    @Override
    public void setToken(String token) {
        if (user == null) {
            user = downloadUser();
        }
        user.setToken(token);
    }

    @Override
    public boolean isEnable() {
        if (user == null) {
            user = downloadUser();
        }
        return user.isEnable();
    }

    @Override
    public void setEnable(boolean enable) {
        if (user == null) {
            user = downloadUser();
        }
        user.setEnable(enable);
    }

    private UserImpl downloadUser() {
        return (UserImpl) userService.getUserByID(id);
    }
}
