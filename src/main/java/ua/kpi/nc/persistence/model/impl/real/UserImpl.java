package ua.kpi.nc.persistence.model.impl.real;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private Long expireDate;

    /******************** UserDetails */

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    /***********************************/

    public UserImpl(Long id, String email, String firstName, String secondName, String lastName, String password,
                    Set<Role> roles, Set<SocialInformation> socialInformations, Long expireDate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.roles = roles;
        this.password = password;
        this.socialInformations = socialInformations;
        this.expireDate = expireDate;
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

    public UserImpl(String email, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, Set<Role> roles, Long expireDate) {
        this.email = email;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.roles = roles;
        this.expireDate = expireDate;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return id;
    }

    @JsonIgnore
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

    @JsonIgnore
    @Override
    public String getConfirmToken() {
        return confirmToken;
    }

    @JsonIgnore
    @Override
    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    @JsonIgnore
    @Override
    public boolean isActive() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @JsonIgnore
    @Override
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    @JsonIgnore
    @Override
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonIgnore
    @Override
    public String getEmail() {
        return email;
    }

    @JsonProperty
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @Override
    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonIgnore
    @Override
    public String getSecondName() {
        return secondName;
    }

    @JsonIgnore
    @Override
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @JsonIgnore
    @Override
    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @JsonIgnore
    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonProperty
    @Override
    public Long getExpireDate() {
        return expireDate;
    }

    @JsonIgnore
    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    @JsonProperty
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonProperty
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonProperty
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }

    @JsonProperty
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
