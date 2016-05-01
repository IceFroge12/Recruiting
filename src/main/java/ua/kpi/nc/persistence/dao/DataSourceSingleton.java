package ua.kpi.nc.persistence.dao;

import org.postgresql.ds.PGPoolingDataSource;
import ua.kpi.nc.config.PropertiesReader;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DataSourceSingleton {

    public static class DataSourceSingletonHolder{
        static DataSourceSingleton HOLDER_INSTANCE = new DataSourceSingleton();
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

    private DataSourceSingleton() {
        try {
            dataSource = setUpDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

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

    public PGPoolingDataSource getDataSource(){
        return dataSource;
    }
}
