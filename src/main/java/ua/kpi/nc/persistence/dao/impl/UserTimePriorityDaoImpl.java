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
public class UserTimePriorityDaoImpl extends  JdbcDaoSupport implements UserTimePriorityDao {
    private static Logger log = LoggerFactory.getLogger(UserTimePriorityDaoImpl.class.getName());
    public UserTimePriorityDaoImpl(DataSource dataSource) {this.setJdbcTemplate(new JdbcTemplate(dataSource));}

    private static final String GET_BY_USER_ID_TIME_POINT_ID = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.id = ?;";

    @Override
    public UserTimePriority getByUserPriority(User user, ScheduleTimePoint scheduleTimePoint) {
        return null;
    }

    @Override
    public Long insertUserPriority(User user, ScheduleTimePoint scheduleTimePoint) {
        return null;
    }

    @Override
    public int updateUserPriority(User user, ScheduleTimePoint scheduleTimePoint) {
        return 0;
    }

    @Override
    public int deleteUserPriority(User user, ScheduleTimePoint scheduleTimePoint) {
        return 0;
    }

    @Override
    public Set<UserTimePriority> getAllTimePoints() {
        return null;
    }

    private class UserTimePriorityExtractor implements ResultSetExtractor<UserTimePriority> {
        @Override
        public UserTimePriority extractData(ResultSet resultSet) throws SQLException {
            UserTimePriority userTimePriority = new UserTimePriority();
            userTimePriority.setUser(new UserProxy(resultSet.getLong("user_id")));
            userTimePriority.setScheduleTimePoint(new ScheduleTimePointProxy(resultSet.getLong("time_point_id")));
            userTimePriority.setTimePriorityType(new TimePriorityType(resultSet.getLong("priority_type_id"),resultSet.getString("choice")));
            return userTimePriority;
        }
    }


}
