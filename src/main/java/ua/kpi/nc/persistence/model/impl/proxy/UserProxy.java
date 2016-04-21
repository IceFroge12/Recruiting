package ua.kpi.nc.persistence.model.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class UserProxy implements User {

    private Long id;

    private UserImpl user;

//    @Autowired
//    private UserService userService;

    private UserService userService;

    public UserProxy() {
        userService = ServiceFactory.getUserService();
    }

    public UserProxy(Long id) {
        this();
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
    public String getEmail() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        if (user == null) {
            user = downloadUser();
        }
        user.setEmail(email);
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
    public String getConfirmToken() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getConfirmToken();
    }

    @Override
    public void setConfirmToken(String confirmToken) {
        if (user == null) {
            user = downloadUser();
        }
        user.setConfirmToken(confirmToken);

    }

    @Override
    public boolean isActive() {
        if (user == null) {
            user = downloadUser();
        }
        return user.isActive();
    }

    @Override
    public void setActive(boolean active) {
        if (user == null) {
            user = downloadUser();
        }
        user.setActive(active);

    }

    @Override
    public Timestamp getRegistrationDate() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getRegistrationDate();
    }

    @Override
    public void setRegistrationDate(Timestamp registrationDate) {
        if (user == null) {
            user = downloadUser();
        }
        user.setRegistrationDate(registrationDate);

    }

    @Override
    public String getSecondName() {
        if (user == null){
            user = downloadUser();
        }
        return user.getSecondName();
    }

    @Override
    public void setSecondName(String secondName) {
        if (user == null){
            user = downloadUser();
        }
        user.setSecondName(secondName);
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
        user.setLastName(lastName);
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
    public Set<SocialInformation> getSocialInformations() {
        if (user == null) {
            user = downloadUser();
        }
        return user.getSocialInformations();
    }

    @Override
    public void setSocialInformations(Set<SocialInformation> socialInformations) {
        if (user == null) {
            user = downloadUser();
        }
        user.setSocialInformations(socialInformations);
    }

    private UserImpl downloadUser() {
        return (UserImpl) userService.getUserByID(id);
    }
}
