package ua.kpi.nc.domain.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class UserImpl implements User {

    private static final long serialVersionUID = -5190252598383342478L;

    private Long id;

    @Pattern(regexp="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}",
        message = "Invalid email address.")
    private String username;
    @Size(min = 3, max = 20,
        message = "The password mast be at least 6 characters long.")
    private String password;

    @Size(min=2, max=50,
            message="Your first name must be between 3 and 50 characters long.")
    private String firstName;

    @Size(min=2, max=50,
            message="Your last name must be between 3 and 50 characters long.")
    private String lastName;

    private String token;

    private boolean enable;

    private Date expirationTime;

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    private Set<Role> roles;

    public UserImpl() {
    }

    public UserImpl(Long id, String username, String password, String firstName, String lastName, String token,
                    boolean enable, Date expirationTime, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.enable = enable;
        this.expirationTime = expirationTime;
        this.roles = roles;
    }

    public UserImpl(Long id, String username, String password, String firstName, String lastName, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
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
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<Role> roles) {
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

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "User:" +
                " firstName= " + firstName  +
                ", lastName= " + lastName + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(username, user.username)
                .append(password, user.password)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .append(password)
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }
}
