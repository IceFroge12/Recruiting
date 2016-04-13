package ua.kpi.nc.domain.model;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface Role extends Model {

    String getRoleName();

    void setRoleName(String roleName);

    Set<User> getUsers();

    void setUsers(Set<User> users);

}
