package ua.kpi.nc.service;

import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.service.impl.RoleServiceImpl;
import ua.kpi.nc.service.impl.UserServiceImpl;

/**
 * Created by dima on 20.04.16.
 */
public class ServiceFactory {

    public static UserService getUserService(){
        return new UserServiceImpl(DaoFactory.getUserDao());
    }

    public static RoleService getRoleService(){
        return new RoleServiceImpl();
    }

}
