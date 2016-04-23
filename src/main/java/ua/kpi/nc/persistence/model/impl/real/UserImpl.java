package ua.kpi.nc.persistence.model.impl.real;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class UserImpl implements User {


    private static final long serialVersionUID = -5190252598383342478L;

    private Long id;

    private String email;

    private String firstName;

    private String secondName;

    private String lastName;

    private Set<Role> roles;

    private String confirmToken;

    private boolean isActive;

    private Timestamp registrationDate;

    private String password;

    private Set<SocialInformation> socialInformations;

    public UserImpl(Long id, String email, String firstName, String secondName, String lastName, String password,
                    Set<Role> roles, Set<SocialInformation> socialInformations) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
        this.password = password;
        this.socialInformations = socialInformations;
    }

    public UserImpl() {
    }

    public UserImpl(String email, String firstName, String secondName, String lastName, Set<Role> roles,
                    String confirmToken, boolean isActive, Timestamp registrationDate, String password,
                    Set<SocialInformation> socialInformations) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
        this.confirmToken = confirmToken;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.password = password;
        this.socialInformations = socialInformations;
    }

    public UserImpl(String email, String firstName, String secondName, String lastName, String password, boolean isActive,
                    Timestamp registrationDate) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.password = password;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
    }

    public UserImpl(String email, String firstName, String secondName, String lastName, Set<Role> roles) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public Set<SocialInformation> getSocialInformations() {
        return socialInformations;
    }

    @JsonIgnore
    @Override
    public void setSocialInformations(Set<SocialInformation> socialInformations) {
        this.socialInformations = socialInformations;
    }

    @Override
    public String getConfirmToken() {
        return confirmToken;
    }

    @Override
    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSecondName() {
        return secondName;
    }

    @Override
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User:" +
                ", email= " + email +
                ", firstName= " + firstName +
                ", lastName= " + lastName  +
                ", registrationDate= " + registrationDate +
                ", isActive= " + isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(email, user.email)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(email)
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }
}
