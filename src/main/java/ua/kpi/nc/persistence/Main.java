package ua.kpi.nc.persistence;

import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.impl.UserServiceImpl;

import java.sql.Timestamp;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        User user = new UserImpl();
        user.setActive(true);
        user.setConfirmToken("test");
        user.setEmail("test");
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        user.setPassword("test");
        user.setFirstName("test");
        user.setSecondName("test");
        user.setLastName("test");
        Role role = new RoleImpl();
        role.setId(1L);
        UserService userService = new UserServiceImpl(DaoFactory.getUserDao());
        userService.insertUser(user, role);
    }
}
