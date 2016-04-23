package ua.kpi.nc.persistence.dao;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DataSourceFactory {
    private static PGPoolingDataSource dataSource;
    private static DataSourceFactory dataSourceFactory;

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

    private DataSourceFactory() {
        try {
            dataSource = getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getInstance(){
        if(dataSourceFactory == null){
            dataSourceFactory = new DataSourceFactory();
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
