package ua.kpi.nc.persistence.dao.impl;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.UserTimePriorityDao;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.proxy.ScheduleTimePointProxy;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author Korzh
 */
public class UserTimePriorityDaoImpl extends JdbcDaoSupport implements UserTimePriorityDao {
    private static Logger log = LoggerFactory.getLogger(UserTimePriorityDaoImpl.class.getName());

    public UserTimePriorityDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private static final String GET_BY_USER_ID_TIME_POINT_ID = "SELECT p.id_user, p.id_time_point, p.id_priority_type, pt.choice " +
            "FROM public.user_time_priority p join public.time_priority_type pt on (p.id_priority_type= pt.id) " +
            "WHERE p.id_user= ? and  p.id_time_point=? ;";
    private static final String INSERT_USER_TIME_PRIORITY = "INSERT INTO user_time_priority (id_user, id_time_point, id_priority_type) VALUES (?,?,?);";
    private static final String UPDATE_USER_TIME_PRIORITY = "UPDATE user_time_priority p set id_priority_type = ? WHERE p.id_user = ? and p.id_time_point = ?;";
    private static final String DELETE_USER_TIME_PRIORITY = "DELETE FROM public.user_time_priority p WHERE p.id_user = ? and p.id_time_point = ?;";
    private static final String GET_ALL_USER_TIME_PRIORITY = "SELECT p.id_user, p.id_time_point, p.id_priority_type, pt.choice " +
            "FROM public.user_time_priority p join public.time_priority_type pt on (p.id_priority_type= pt.id);";

    @Override
    public UserTimePriority getByUserPriority(User user, ScheduleTimePoint scheduleTimePoint) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for User time priority  with id user,  id  scheduleTimePoint = " + user.getId() + scheduleTimePoint.getId());
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_USER_ID_TIME_POINT_ID,
                new UserTimePriorityExtractor(), user.getId(), scheduleTimePoint.getId());
    }

    @Override
    public Long insertUserPriority(UserTimePriority userTimePriority) {
        if (log.isTraceEnabled()) {
            log.trace("Inserting User time priority  with id user,  id  scheduleTimePoint = " + userTimePriority.getUser().getId() + userTimePriority.getScheduleTimePoint().getId());
        }
        return this.getJdbcTemplate().insert(INSERT_USER_TIME_PRIORITY, userTimePriority.getUser().getId(),
                userTimePriority.getScheduleTimePoint().getId(), userTimePriority.getTimePriorityType().getId());
    }

    @Override
    public int updateUserPriority(UserTimePriority userTimePriority) {
        if (log.isTraceEnabled()) {
            log.trace("Inserting User time priority  with id user,  id  scheduleTimePoint = " + userTimePriority.getUser().getId() + userTimePriority.getScheduleTimePoint().getId());
        }
        return this.getJdbcTemplate().update(UPDATE_USER_TIME_PRIORITY, userTimePriority.getTimePriorityType().getId(), userTimePriority.getUser().getId(),
                userTimePriority.getScheduleTimePoint().getId());
    }

    @Override
    public int deleteUserPriority(UserTimePriority userTimePriority) {
        if (log.isTraceEnabled()) {
            log.trace("Deleting User time priority  with id user,  id  scheduleTimePoint = " + userTimePriority.getUser().getId() + userTimePriority.getScheduleTimePoint().getId());
        }
        return this.getJdbcTemplate().update(DELETE_USER_TIME_PRIORITY, userTimePriority.getUser().getId(),
                userTimePriority.getScheduleTimePoint().getId());
    }

    @Override
    public Set<UserTimePriority> getAllTimePoints() {
        if (log.isTraceEnabled()) {
            log.trace("Getting all User time priorities ");
        }
        return this.getJdbcTemplate().queryForSet(GET_ALL_USER_TIME_PRIORITY, new UserTimePriorityExtractor());
    }

    private class UserTimePriorityExtractor implements ResultSetExtractor<UserTimePriority> {
        @Override
        public UserTimePriority extractData(ResultSet resultSet) throws SQLException {
            UserTimePriority userTimePriority = new UserTimePriority();
            userTimePriority.setUser(new UserProxy(resultSet.getLong("id_user")));
            userTimePriority.setScheduleTimePoint(new ScheduleTimePointProxy(resultSet.getLong("id_time_point")));
            userTimePriority.setTimePriorityType(new TimePriorityType(resultSet.getLong("id_priority_type"), resultSet.getString("choice")));
            return userTimePriority;
        }
    }


}
