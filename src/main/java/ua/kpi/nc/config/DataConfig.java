package ua.kpi.nc.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.kpi.nc")
@PropertySource("classpath:app.properties")
@EnableLoadTimeWeaving
@EnableSpringConfigured
public class DataConfig {

    @Value("${db.password}")
    private String databasePassword;
    @Value("${db.server.name}")
    private String databaseServerName;
    @Value("${db.username}")
    private String databaseUsername;
    @Value("${db.name}")
    private String databaseName;

    @Bean
    public DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("DataSource");
        dataSource.setServerName(databaseServerName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(databaseUsername);
        dataSource.setPassword(databasePassword);
        dataSource.setMaxConnections(20);
        return dataSource;
    }
}
