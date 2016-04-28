package ua.kpi.nc.persistence.dao;

import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DataSourceSingleton {
    private static PGPoolingDataSource dataSource;
    private static volatile DataSourceSingleton dataSourceSingleton;

    private static Logger log = LoggerFactory.getLogger(DataSourceSingleton.class.getName());

    @Value("${db.password}")
    private static String databasePassword;
    @Value("${db.server.name}")
    private static String databaseServerName;
    @Value("${db.email}")
    private static String databaseUsername;
    @Value("${db.name}")
    private static String databaseName;
    @Value("${db.connections}")
    private static int maxConnections;

    private DataSourceSingleton() {
        try {
            dataSource = getDataSource();
        } catch (NamingException e) {
            log.error("Cannot get data source",e);
        }
    }

    public static DataSource getInstance(){
        DataSourceSingleton localInstance = dataSourceSingleton;
        if (localInstance == null) {
            synchronized (DataSourceSingleton.class) {
                localInstance = dataSourceSingleton;
                if (localInstance == null) {
                    dataSourceSingleton = localInstance = new DataSourceSingleton();
                }
            }
        }
        return dataSource;
    }
    // NEEDD DOWNLOAD FROM PROPERTIES
    private PGPoolingDataSource getDataSource() throws NamingException {
        dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("dataSource");
        dataSource.setServerName("localhost:5434");
        dataSource.setDatabaseName("recruiting");
        dataSource.setUser("admin6qvqzkw");
        dataSource.setPassword("w_-tKa4R-pAJ");
        dataSource.setMaxConnections(20);
        return dataSource;
    }
}
