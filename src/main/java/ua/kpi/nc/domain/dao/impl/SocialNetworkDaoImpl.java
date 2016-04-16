package ua.kpi.nc.domain.dao.impl;
import org.apache.log4j.Logger;
import ua.kpi.nc.domain.dao.SocialNetwork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by IO on 16.04.2016.
 */
public class SocialNetworkDaoImpl extends DaoSupport implements SocialNetwork {

    private static Logger log = Logger.getLogger(SocialNetworkDaoImpl.class.getName());

    public SocialNetworkDaoImpl() {

    }

    @Override
    public SocialNetwork getByID(Long id) {
        String sql = "SELECT * FROM \"social_network\" WHERE \"social.network\".id = " + id;
        log.trace("Looking for social network with id = " + id);
        SocialNetwork socialNetwork = null;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            log.trace("Open connection");
            log.trace("Prepared statement creating");
            log.trace("Get result set");
            if (resultSet.next()){
                log.trace("Create social network to return");
            }
        }
    }
}
