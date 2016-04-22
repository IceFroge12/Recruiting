package ua.kpi.nc.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.SocialNetworkDao;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

/**
 * Created by IO on 16.04.2016.
 */
public class SocialNetworkDaoImpl extends JdbcDaoSupport implements SocialNetworkDao {

	private static Logger log = LoggerFactory.getLogger(SocialNetworkDaoImpl.class.getName());

	private static final String SQL_GET_BY_ID = "SELECT s.id, s.title" + "FROM \"social_network\" s\n"
			+ "WHERE s.id = ?";

	public SocialNetworkDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public SocialNetwork getByID(Long id) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for social network with id = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new SocialNetworkExtractor(), id);
	}

	private final class SocialNetworkExtractor implements ResultSetExtractor<SocialNetwork> {

		@Override
		public SocialNetwork extractData(ResultSet resultSet) throws SQLException {
			SocialNetwork socialNetwork = new SocialNetwork(resultSet.getLong("id"), resultSet.getString("title"));
			return socialNetwork;
		}

	}
}
