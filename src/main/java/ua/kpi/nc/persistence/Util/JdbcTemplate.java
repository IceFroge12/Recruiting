package ua.kpi.nc.persistence.util;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class JdbcTemplate {

    private DataSource dataSource;

    private static Logger log = Logger.getLogger(JdbcTemplate.class.getName());

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

    public <T> T queryForObject(String sql, ResultSetExtractor<T> resultSetExtractor) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            log.trace("Open connection");
            log.trace("Create prepared statement");
            log.trace("Get result");
            if (resultSet.next()) {
                return resultSetExtractor.extractData(resultSet);
            }
            return null;
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return null;
        }
    }

    public  <T> T queryWithParameters(String sql, ResultSetExtractor<T> resultSetExtractor, Object... objects) {
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
            if (resultSet.next()) {
                return resultSetExtractor.extractData(resultSet);
            }
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return null;
        }
        return null;
    }
}
