package ua.kpi.nc.persistence.dto;

import ua.kpi.nc.persistence.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by IO on 05.05.2016.
 */
public class CurrentAuthUser {
    private Long id;
    private String firstName;
    private Set<Role> roles;

    public CurrentAuthUser(Long id, String firstName, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.roles = roles;
    }



    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
