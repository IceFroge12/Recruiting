package ua.kpi.nc.persistence.model.impl.proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.Set;

/**
 * Created by Chalienko on 15.04.2016.
 */
public class RoleProxy implements Role {

    private Long id;

    private RoleImpl role;

    private RoleService roleService;

    public  RoleProxy(Long id) {
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
    public String getRoleName() {
        checkRoleForExist();
        return role.getRoleName();
    }

    @Override
    public void setRoleName(String roleName) {
        checkRoleForExist();
        role.setRoleName(roleName);
    }
    @JsonIgnore
    @Override
    public Set<User> getUsers() {
        checkRoleForExist();
        return role.getUsers();
    }

    @Override
    public void setUsers(Set<User> users) {
        checkRoleForExist();
        role.setUsers(users);
    }

    private void checkRoleForExist(){
        if (role == null) {
            roleService = ServiceFactory.getRoleService();
            role = downloadRole();
        }
    }

    @Override
    public String getAuthority() {
        return this.getRoleName();
    }

    private RoleImpl downloadRole() {
        return (RoleImpl) roleService.getRoleById(id);
    }

    @Override
    public String toString() {
        return "RoleProxy{" +
                "role=" + role +
                '}';
    }
}
