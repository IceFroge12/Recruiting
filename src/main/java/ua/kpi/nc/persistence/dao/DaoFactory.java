package ua.kpi.nc.persistence.dao;


import ua.kpi.nc.persistence.dao.impl.RecruitmentDaoImpl;
import ua.kpi.nc.persistence.dao.impl.UserDaoImpl;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DaoFactory {
    public static UserDao getUserDao(){
        return new UserDaoImpl(DataSourceFactory.getInstance());
    }

    public static RecruitmentDAO getRecruitmentDao(){
        return new RecruitmentDaoImpl(DataSourceFactory.getInstance());
    }
}
