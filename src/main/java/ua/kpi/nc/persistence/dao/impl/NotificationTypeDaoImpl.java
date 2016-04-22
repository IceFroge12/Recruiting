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
    private static final String GET_BY_ID = "SELECT n.id, n.n_title FROM public.notification_type  n " +
            "WHERE n.id = ?;";
    private static final String GET_BY_TITLE = "SELECT n.id, n.n_title FROM public.notification_type  n " +
            "WHERE n.n_title = ?;";
    private static final String UPDATE_NOTIFICATION_TYPE = "UPDATE public.notification_type SET n_title = ? " +
            "WHERE notification_type.id = ?;";
    private static final String DELETE_NOTIFICATION_TYPE = "DELETE FROM public.notification_type WHERE id = ?;";
    private static final String GET_ALL = "SELECT n.id, n.n_title FROM public.notification_type  n ";

    private static Logger log = LoggerFactory.getLogger(NotificationTypeDaoImpl.class.getName());

    public NotificationTypeDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public NotificationType getById(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form notification type with id  = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID, new NotificationTypeExtractor(), id);
    }

    @Override
    public NotificationType getByTitle(String title) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form notification type with title  = " + title);
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_TITLE, new NotificationTypeExtractor(), title);
    }

    @Override
    public int updateNotificationType(NotificationType notificationType) {
        if (log.isTraceEnabled()) {
            log.trace("Updating notification type with id  = " + notificationType.getId());
        }
        return this.getJdbcTemplate().update(UPDATE_NOTIFICATION_TYPE, notificationType.getId());
    }

    @Override
    public int deleteNotificationType(NotificationType notificationType) {
        if (log.isTraceEnabled()) {
            log.trace("Deleting notification type with id  = " + notificationType.getId());
        }
        return this.getJdbcTemplate().update(DELETE_NOTIFICATION_TYPE, notificationType.getId());
    }

    @Override
    public Set<NotificationType> getAll() {
        if (log.isTraceEnabled()) {
            log.trace("Getting All Notification Types");
        }
        return this.getJdbcTemplate().queryForSet(GET_ALL, new NotificationTypeExtractor());
    }

    private static final class NotificationTypeExtractor implements ResultSetExtractor<NotificationType> {
        @Override
        public NotificationType extractData(ResultSet resultSet) throws SQLException {
            NotificationType notificationType = new NotificationType();
            notificationType.setId(resultSet.getLong("id"));
            notificationType.setTitle(resultSet.getString("title"));
            return notificationType;
        }
    }
}
