package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.SocialNetworkDao;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;

/**
 * Created by IO on 16.04.2016.
 */
public class SocialNetworkDaoImpl implements SocialNetworkDao {

    private final JdbcDaoSupport jdbcDaoSupport;

    private static Logger log = LoggerFactory.getLogger(SocialNetworkDaoImpl.class.getName());

    private ResultSetExtractor<SocialNetwork> extractor = resultSet -> {
        SocialNetwork socialNetwork;
        socialNetwork = new SocialNetwork(resultSet.getLong("id"), resultSet.getString("title"));
        return socialNetwork;
    };

    private static final String SQL_GET_BY_ID = "SELECT s.id, s.title" + "FROM \"social_network\" s\n"
            + "WHERE s.id = ?";

    public SocialNetworkDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public SocialNetwork getByID(Long id) {
        log.info("Looking for social network with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

}
