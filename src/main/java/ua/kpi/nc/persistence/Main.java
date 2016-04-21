package ua.kpi.nc.persistence;

import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.UserDao;
import ua.kpi.nc.persistence.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 22.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        UserDao userDao = DaoFactory.getUserDao();
        Set<User> userList = userDao.getAll();
        for (User user : userList){
            System.out.println(user);
        }
        User user = userDao.getByUsername("test2");
        User user2 = userDao.getByID(5L);
        System.out.println(user);
        System.out.println(user2);
    }
}
