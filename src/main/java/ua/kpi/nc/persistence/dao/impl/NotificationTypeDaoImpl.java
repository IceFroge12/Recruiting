package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.NotificationTypeDao;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author Korzh
 */
public class NotificationTypeDaoImpl extends JdbcDaoSupport implements NotificationTypeDao {

	static final String ID_COL = "id";
	static final String TITLE_COL = "n_title";

	static final String TABLE_NAME = "notification_type";

	private static final String SQL_GET = "SELECT n." + ID_COL + ", n." + TITLE_COL + " FROM public." + TABLE_NAME
			+ "  n ";
	private static final String SQL_GET_BY_ID = SQL_GET + " WHERE n." + ID_COL + " = ?;";
	private static final String SQL_GET_BY_TITLE = SQL_GET + " WHERE n." + TITLE_COL + " = ?;";
	private static final String SQL_UPDATE_NOTIFICATION_TYPE = "UPDATE public." + TABLE_NAME + " SET " + TITLE_COL
			+ " = ? " + "WHERE " + TABLE_NAME + "." + ID_COL + " = ?;";
	private static final String SQL_DELETE_NOTIFICATION_TYPE = "DELETE FROM public." + TABLE_NAME + " WHERE " + ID_COL
			+ " = ?;";

	private static Logger log = LoggerFactory.getLogger(NotificationTypeDaoImpl.class.getName());

	private static final class NotificationTypeExtractor implements ResultSetExtractor<NotificationType> {
		@Override
		public NotificationType extractData(ResultSet resultSet) throws SQLException {
			NotificationType notificationType = new NotificationType();
			notificationType.setId(resultSet.getLong(ID_COL));
			notificationType.setTitle(resultSet.getString(TITLE_COL));
			return notificationType;
		}
	}

	public NotificationTypeDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public NotificationType getById(Long id) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for form notification type with id  = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new NotificationTypeExtractor(), id);
	}

	@Override
	public NotificationType getByTitle(String title) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for form notification type with title  = " + title);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, new NotificationTypeExtractor(), title);
	}

	@Override
	public int updateNotificationType(NotificationType notificationType) {
		if (log.isTraceEnabled()) {
			log.trace("Updating notification type with id  = " + notificationType.getId());
		}
		return this.getJdbcTemplate().update(SQL_UPDATE_NOTIFICATION_TYPE, notificationType.getId());
	}

	@Override
	public int deleteNotificationType(NotificationType notificationType) {
		if (log.isTraceEnabled()) {
			log.trace("Deleting notification type with id  = " + notificationType.getId());
		}
		return this.getJdbcTemplate().update(SQL_DELETE_NOTIFICATION_TYPE, notificationType.getId());
	}

	@Override
	public Set<NotificationType> getAll() {
		if (log.isTraceEnabled()) {
			log.trace("Getting All Notification Types");
		}
		return this.getJdbcTemplate().queryForSet(SQL_GET, new NotificationTypeExtractor());
	}

}
