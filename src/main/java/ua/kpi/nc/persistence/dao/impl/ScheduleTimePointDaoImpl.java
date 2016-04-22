package ua.kpi.nc.persistence.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ScheduleTimePointDao;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.model.impl.real.ScheduleTimePointImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Korzh
 */
public class ScheduleTimePointDaoImpl extends JdbcDaoSupport implements ScheduleTimePointDao {
    private static Logger log = LoggerFactory.getLogger(ScheduleTimePointDaoImpl.class.getName());
    private static final String USERS_FINAL_TIME_QUERY = "SELECT u.id, u.email, u.first_name,u.last_name,u.second_name," +
            " u.password, u.confirm_token, u.is_active, u.registration_date " +
            "FROM public.user u join public.user_time_final f on u.id=f.id_user join public.schedule_time_point s on  f.id_time_point= s.id Where s.id=?;";
    private static final String USER_TIME_PRIORITY = "select  utp.id_user, utp.id_time_point, utp.id_priority_type, " +
            "tpt.choice  from user_time_priority utp inner join time_priority_type tpt on tpt.id = utp.id_priority_type " +
            "where utp.id_time_point = ?;";
    private static final String SCHEDULE_TIME_POINT_BY_USER_ID = "SELECT u.id, FROM public.user u " +
            "join public.user_time_priority p on u.id=p.id_user join public.schedule_time_point s on  p.id_time_point= s.id Where u.id=?;";

    public ScheduleTimePointDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private static final String GET_BY_ID = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.id = ?;";

    @Override
    public ScheduleTimePoint getFinalTimePointById(Long id) {
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID, new ScheduleTimePointExtractor(), id);
    }

    @Override
    public Set<ScheduleTimePoint> getScheduleTimePointByUserId(Long id) {
        return this.getJdbcTemplate().queryForSet(SCHEDULE_TIME_POINT_BY_USER_ID, new ScheduleTimePointExtractor(), id);
    }

    @Override
    public Long insertScheduleTimePoint(ScheduleTimePointDao scheduleTimePointDao) {
        return null;
    }

    @Override
    public int updateScheduleTimePoint(ScheduleTimePointDao scheduleTimePointDao) {
        return 0;
    }

    @Override
    public int deleteScheduleTimePoint(ScheduleTimePointDao scheduleTimePointDao) {
        return 0;
    }

    private Set<User> getUsersFinalInTimePoint(Long timeID) {
        return this.getJdbcTemplate().queryWithParameters(USERS_FINAL_TIME_QUERY, resultSet ->
        {
            Set<User> set = new HashSet<>();
            do {
                set.add(new UserProxy(resultSet.getLong("id")));
            } while (resultSet.next());
            return set;
        }, timeID);
    }

    private Set<UserTimePriority> getUserTimePriority(Long timeID, ScheduleTimePoint scheduleTimePoint) {
        return this.getJdbcTemplate().queryWithParameters(USER_TIME_PRIORITY, resultSet ->
        {
            Set<UserTimePriority> set = new HashSet<>();
            do {
                set.add(new UserTimePriority(new UserProxy(resultSet.getLong("id_user")),
                        scheduleTimePoint, new TimePriorityType(resultSet.getLong("id_priority_type"), resultSet.getString("choice"))));
            } while (resultSet.next());
            return set;
        }, timeID);
    }

    private class ScheduleTimePointExtractor implements ResultSetExtractor<ScheduleTimePoint> {
        @Override
        public ScheduleTimePoint extractData(ResultSet resultSet) throws SQLException {
            ScheduleTimePoint scheduleTimePoint = new ScheduleTimePointImpl();
            scheduleTimePoint.setId(resultSet.getLong("id"));
            scheduleTimePoint.setTimePoint(resultSet.getTimestamp("tp"));
            scheduleTimePoint.setUserTimePriorities(getUserTimePriority(resultSet.getLong("id"), scheduleTimePoint));
            scheduleTimePoint.setUsers(getUsersFinalInTimePoint(resultSet.getLong("id")));
            return scheduleTimePoint;
        }
    }


}
