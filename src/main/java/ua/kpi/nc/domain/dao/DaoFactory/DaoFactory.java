package ua.kpi.nc.domain.dao.DaoFactory;


import ua.kpi.nc.domain.dao.impl.PostgreSQLImpl.PostgreSQLDAOFactory;
import ua.kpi.nc.domain.dao.impl.PostgreSQLImpl.SocialNetworkDAO;
import ua.kpi.nc.domain.model.SocialNetwork;

/**
 * Created by IO on 19.04.2016.
 */
public abstract class DAOFactory {

    public abstract SocialNetworkDAO<SocialNetwork, Long> getSocialNetworkDAO();

    public static DAOFactory getDAOFactory(DaoFactoryType daoFactoryType){
        switch (daoFactoryType){
            case PostgreSQL:
                return new PostgreSQLDAOFactory();
            default:
                return null;
        }
    }
}
