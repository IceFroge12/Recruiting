package ua.kpi.nc.persistence.dto;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 27.04.16.
 */
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String secondName;
    private String lastName;
    private String password;
    private List<RoleImpl> roleList;

    public UserDto() {

    }

    public UserDto(String email, String firstName, String secondName, String lastName, List<RoleImpl> roleList) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.password = password;
    }

    public UserDto(String email, String firstName, String secondName, String lastName, String password, List<RoleImpl> roleList) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.password = password;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<RoleImpl> getRoleList() {

        return roleList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleList(List<RoleImpl> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "roleList=" + roleList +
                ", lastName='" + lastName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
