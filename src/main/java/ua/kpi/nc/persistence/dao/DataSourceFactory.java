package ua.kpi.nc.persistence.dao;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DataSourceFactory {
    public static PGPoolingDataSource dataSource;

    @Value("${db.password}")
    private String databasePassword;
    @Value("${db.server.name}")
    private String databaseServerName;
    @Value("${db.email}")
    private String databaseUsername;
    @Value("${db.name}")
    private String databaseName;
    @Value("${db.connections}")
    private int maxConnections;

    private DataSourceFactory() {
        try {
            dataSource = getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getInstance(){
        return dataSource;
    }

    private PGPoolingDataSource getDataSource() throws NamingException {
        dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("dataSource");
        dataSource.setServerName(databaseServerName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(databaseUsername);
        dataSource.setPassword(databasePassword);
        dataSource.setMaxConnections(maxConnections);
        return dataSource;
    }
}
