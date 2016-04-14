package ua.kpi.nc.domain.dao;

import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface UserDao {

    User getByUsername(String username) throws DaoException;

    User getByID(Long id) throws DaoException;

    boolean isExist(String username) throws DaoException;

    void insertUser(User user, Role role) throws DaoException;

    void deleteUser(User user) throws DaoException;
}
