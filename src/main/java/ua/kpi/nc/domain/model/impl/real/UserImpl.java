package ua.kpi.nc.domain.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class UserImpl implements User {

    private static final long serialVersionUID = -5190252598383342478L;

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String last_name;

    private Set<Role> roles;

    public UserImpl() {
    }

    public UserImpl(Long id,String username, String password, String firstName, String last_name, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.last_name = last_name;
        this.roles = roles;
    }
    @Override
    public String getLastName() {
        return last_name;
    }

    @Override
    public void setLastName(String lastName) {
        this.last_name = lastName;
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
    public String toString() {
        return "User:" +
                " firstName= " + firstName  +
                ", lastName= " + last_name + "\n";
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
                .append(last_name, user.last_name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .append(password)
                .append(firstName)
                .append(last_name)
                .toHashCode();
    }
}
