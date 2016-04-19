package ua.kpi.nc.domain.dao.impl.PostgreSQLImpl;

import org.springframework.stereotype.Component;
import ua.kpi.nc.domain.model.SocialNetwork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IO on 19.04.2016.
 */

public class SocialNetworkDAOImpl extends PostgreSQLGenericDaoOnePKImpl<ua.kpi.nc.domain.model.SocialNetwork, Long>
        implements SocialNetworkDAO<SocialNetwork, Long> {

    public SocialNetworkDAOImpl(Connection connection) {
        super(connection);
    }

    private static Logger log = Logger.getLogger(SocialNetworkDAOImpl.class.getName());

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getPKAttribute() {
        return "id";
    }

    @Override
    protected Long getPKValue(ua.kpi.nc.domain.model.SocialNetwork object) {
        return object.getId();
    }

    @Override
    protected void additionActionForInsert(ua.kpi.nc.domain.model.SocialNetwork object) throws SQLException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement preparedStatement, ua.kpi.nc.domain.model.SocialNetwork object) throws SQLException {
        preparedStatement.setString(2, object.getTitle());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement preparedStatement, ua.kpi.nc.domain.model.SocialNetwork object) throws SQLException {
        preparedStatement.setLong(1, object.getId());
        preparedStatement.setString(2, object.getTitle());
    }

    @Override
    protected List<ua.kpi.nc.domain.model.SocialNetwork> parseResultSet(ResultSet resultSet) throws SQLException {
        List<ua.kpi.nc.domain.model.SocialNetwork> socialNetworks = new LinkedList<>();
        while (resultSet.next()){
            ua.kpi.nc.domain.model.SocialNetwork socialNetwork = new ua.kpi.nc.domain.model.SocialNetwork(
                    resultSet.getLong("id"),
                    resultSet.getString("title")
            );
            socialNetworks.add(socialNetwork);
        }
        return socialNetworks;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getReadQuery() {
        return "SELECT * FROM \"social_network\"";
    }

    @Override
    protected String getCreateQuery() {
        return null;
    }

    @Override
    public ua.kpi.nc.domain.model.SocialNetwork getByTitle(String title) {
        String sql = getReadQuery();
        sql += " WHERE title = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            return customRead(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //TODO fix
        return null;
    }
}
