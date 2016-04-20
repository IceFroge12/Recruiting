package ua.kpi.nc.persistence.util;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;

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

    public boolean updateWithTransaction(String[] sqls, Object... objects) {
        try (Connection connection = dataSource.getConnection()) {
            log.trace("Open connection");
            connection.setAutoCommit(false);
            int valueNum = 0;
            for (String sql : sqls) {
                try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    log.trace("Create prepared statement");
                    log.trace("Get result");
                    int rowNum = 1;
                    int i;
                    for (i = valueNum; i < statement.getParameterMetaData().getParameterCount() + valueNum; i++) {
                        statement.setObject(rowNum++, objects[i]);
                    }
                    valueNum = i;
                    statement.executeUpdate();
                } catch (SQLException e) {
                    connection.rollback();
                    log.error("Cannot read objects", e);
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            log.error("Cannot read objects", e);
            return false;
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
