package ua.kpi.nc.domain.model;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface User extends Model {

    String getEmail();

    void setEmail(String email);

    String getFirstName();

    void setFirstName(String firstName);

    String getSecondName();

    void setSecondName(String secondName);

    String getLastName();

    void setLastName(String lastName);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

}
