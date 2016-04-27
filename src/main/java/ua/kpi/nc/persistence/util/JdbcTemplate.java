package ua.kpi.nc.persistence.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class JdbcTemplate {

    private DataSource dataSource;

    private static Logger log = LoggerFactory.getLogger(JdbcTemplate.class.getName());

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int update(String sql, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result");
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return 0;
        }
    }

    public Long insert(String sql, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result");
            return insert(statement, objects);
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return 0L;
        }
    }

    public Long insert(String sql, Connection connection, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            log.trace("Create prepared statement");
            return insert(statement,objects);
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return 0L;
        }
    }

    public <T> List<T> queryForList(String sql,ResultSetExtractor<T> resultSetExtractor, Object... objects){
        return (List<T>) queryForCollection(new HashSet<>(),sql,  resultSetExtractor, (Object[]) objects);
    }

    private <T> Collection<T> queryForCollection(Collection<T> collection,String sql,
                                                 ResultSetExtractor<T> resultSetExtractor, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                collection.add(resultSetExtractor.extractData(resultSet));
            }
            return collection;
        } catch (SQLException e) {
            //TODO logger
            return null;
        }
    }

    public <T> Set<T> queryForSet(String sql, ResultSetExtractor<T> resultSetExtractor, Object... objects){
        return (Set<T>) queryForCollection(new HashSet<>(),sql,  resultSetExtractor, (Object[]) objects);
    }

    private Long insert(PreparedStatement statement,Object... objects) {
        log.trace("Get result");
        int rowNum = 1;
        try {
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("Cannot insert objects", e);
            return null;
        }
        return null;
    }

    public <T> T queryWithParameters(String sql, ResultSetExtractor<T> resultSetExtractor, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            ResultSet resultSet = statement.executeQuery();
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result");
            if (resultSet.next()){
                return resultSetExtractor.extractData(resultSet);
            }
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return null;
        }
        return null;
    }
}
