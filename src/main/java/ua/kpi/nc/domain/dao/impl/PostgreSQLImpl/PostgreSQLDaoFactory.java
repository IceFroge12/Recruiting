package ua.kpi.nc.domain.dao.impl.PostgreSQLImpl;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import ua.kpi.nc.domain.dao.DaoFactory.DAOFactory;
import ua.kpi.nc.domain.dao.impl.PostgreSQLImpl.SocialNetworkDAO;
import ua.kpi.nc.domain.dao.impl.PostgreSQLImpl.SocialNetworkDAOImpl;
import ua.kpi.nc.domain.model.SocialNetwork;

import java.sql.SQLException;

/**
 * Created by IO on 19.04.2016.
 */
public class PostgreSQLDAOFactory extends DAOFactory {

    private String databasePassword = "w_-tKa4R-pAJ";

    private String databaseServerName = "localhost:5432";

    private String databaseUsername = "admin6qvqzkw";

    private String databaseName = "recruiting";

    private PGPoolingDataSource ds;

    public PostgreSQLDAOFactory() {
        this.ds = new PGPoolingDataSource();
        ds.setDataSourceName("ds");
        ds.setServerName(databaseServerName);
        ds.setDatabaseName(databaseName);
        ds.setUser(databaseUsername);
        ds.setPassword(databasePassword);
        ds.setMaxConnections(20);
    }

    @Override
    public SocialNetworkDAO<SocialNetwork, Long> getSocialNetworkDAO() {
        try {
            return new SocialNetworkDAOImpl(ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //TODO fix;
        return null;
    }
}
