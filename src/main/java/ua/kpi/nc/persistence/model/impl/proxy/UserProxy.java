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
        checkUserForExist();
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        checkUserForExist();
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        checkUserForExist();
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        checkUserForExist();
        user.setFirstName(firstName);
    }

    @Override
    public String getConfirmToken() {
        checkUserForExist();
        return user.getConfirmToken();
    }

    @Override
    public void setConfirmToken(String confirmToken) {
        checkUserForExist();
        user.setConfirmToken(confirmToken);

    }

    @Override
    public boolean isActive() {
        checkUserForExist();
        return user.isActive();
    }

    @Override
    public void setActive(boolean active) {
        checkUserForExist();
        user.setActive(active);

    }

    @Override
    public Timestamp getRegistrationDate() {
        checkUserForExist();
        return user.getRegistrationDate();
    }

    @Override
    public void setRegistrationDate(Timestamp registrationDate) {
        checkUserForExist();
        user.setRegistrationDate(registrationDate);

    }

    @Override
    public String getSecondName() {
        checkUserForExist();
        return user.getSecondName();
    }

    @Override
    public void setSecondName(String secondName) {
        checkUserForExist();
        user.setSecondName(secondName);
    }

    @Override
    public String getLastName() {
        checkUserForExist();
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        checkUserForExist();
        user.setLastName(lastName);
    }

    @Override
    public Set<Role> getRoles() {
        checkUserForExist();
        return user.getRoles();
    }

    @Override
    public void setRoles(Set<Role> roles) {
        checkUserForExist();
        user.setRoles(roles);
    }

    @Override
    public String getPassword() {
        checkUserForExist();
        return user.getPassword();
    }

    @Override
    public void setPassword(String password) {
        checkUserForExist();
        user.setPassword(password);
    }

    @Override
    public Set<SocialInformation> getSocialInformations() {
        checkUserForExist();
        return user.getSocialInformations();
    }

    @Override
    public void setSocialInformations(Set<SocialInformation> socialInformations) {
        checkUserForExist();
        user.setSocialInformations(socialInformations);
    }

    private void checkUserForExist() {
        if (user == null) {
            user = downloadUser();
        }
    }

    private UserImpl downloadUser() {
        return (UserImpl) userService.getUserByID(id);
    }
}
