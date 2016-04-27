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

    private static PGPoolingDataSource dataSource;
    private static DataSourceSingleton dataSourceSingleton;

    private static PropertiesReader propertiesReader = PropertiesReader.getInstance();


    private static String databasePassword = propertiesReader.propertiesReader("db.password");

    private static String databaseServerName = propertiesReader.propertiesReader("db.server.name");

    private static String databaseUsername = propertiesReader.propertiesReader("db.username");;

    private static String databaseName = propertiesReader.propertiesReader("db.name");;

    private static   int maxConnections = Integer.parseInt(propertiesReader.propertiesReader("db.connections"));;

    private DataSourceSingleton(){
        try {
            dataSource = getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getInstance(){
        if(dataSourceSingleton == null){
            dataSourceSingleton = new DataSourceSingleton();
        }
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
