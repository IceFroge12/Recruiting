package ua.kpi.nc.domain.model;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface User extends Model {
    String getLastName();

    void setLastName(String lastName);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getFirstName();

    void setFirstName(String firstName);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);
}
