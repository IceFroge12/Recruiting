package ua.kpi.nc.domain.model.impl.proxy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.SocialInformation;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.UserImpl;
import ua.kpi.nc.service.UserService;

import java.sql.Timestamp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserProxy userProxy = (UserProxy) o;

        return new EqualsBuilder()
                .append(id, userProxy.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    private UserImpl downloadUser() {
        return (UserImpl) userService.getUserByID(id);
    }
}
