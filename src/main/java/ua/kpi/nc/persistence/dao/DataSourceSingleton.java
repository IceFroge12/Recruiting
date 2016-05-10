package ua.kpi.nc.persistence.dao;

import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import ua.kpi.nc.config.PropertiesReader;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DataSourceSingleton {

    private static Logger log = LoggerFactory.getLogger(DataSourceSingleton.class.getName());

    private DataSourceSingleton() {
        try {
            dataSource = setUpDataSource();
        } catch (NamingException e) {
            log.error("Cannot set up data source", e);
        }
    }

    private static class DataSourceSingletonHolder{
        static DataSourceSingleton HOLDER_INSTANCE = new DataSourceSingleton();
        private DataSourceSingletonHolder() {
        }
    }

    public static DataSource getInstance() {
        return DataSourceSingletonHolder.HOLDER_INSTANCE.getDataSource();
    }

    private static PGPoolingDataSource dataSource;

    private static PropertiesReader propertiesReader = PropertiesReader.getInstance();

    private static String databasePassword = propertiesReader.propertiesReader("db.password");

    private static String databaseServerName = propertiesReader.propertiesReader("db.server.name");

    private static String databaseUsername = propertiesReader.propertiesReader("db.username");

    private static String databaseName = propertiesReader.propertiesReader("db.name");

    private static int maxConnections = Integer.parseInt(propertiesReader.propertiesReader("db.connections"));


    private PGPoolingDataSource setUpDataSource() throws NamingException {
        dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("dataSource");
        dataSource.setServerName(databaseServerName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(databaseUsername);
        dataSource.setPassword(databasePassword);
        dataSource.setMaxConnections(maxConnections);
        return dataSource;
    }

    private PGPoolingDataSource getDataSource(){
        return dataSource;
    }
}
