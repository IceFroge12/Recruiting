package ua.kpi.nc.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.jdbc2.optional.PoolingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.kpi.nc")
@PropertySource("classpath:app.properties")
public class DataConfig {

    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_SERVER_NAME = "db.server.name";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_DATABASE_NAME = "db.name";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("DataSource");
        dataSource.setServerName(env.getRequiredProperty(PROP_SERVER_NAME));
        dataSource.setDatabaseName(env.getRequiredProperty(PROP_DATABASE_NAME));
        dataSource.setUser(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        dataSource.setMaxConnections(20);
        return dataSource;
    }
}
