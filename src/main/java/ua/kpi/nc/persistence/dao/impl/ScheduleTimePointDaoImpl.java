package ua.kpi.nc.persistence.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ScheduleTimePointDao;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
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
    private static final String USERS_PRIORITY_TIME_QUERY = "SELECT u.id, u.email, u.first_name,u.last_name,u.second_name," +
            " u.password, u.confirm_token, u.is_active, u.registration_date " +
            "FROM public.user u join public.user_time_final f on u.id=f.id_user join public.schedule_time_point s on  f.id_time_point= s.id Where s.id=?;";
    public ScheduleTimePointDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }
private String tempQuery ;
    private static final String GET_BY_ID = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.id = ?;";
    @Override
    public ScheduleTimePoint getFinalTimePointById(Long id) {
        tempQuery = USERS_FINAL_TIME_QUERY;
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID,new SchedulePriorityTimePointExtractor(),id);
}
    @Override
    public ScheduleTimePoint getPriorityTimeById(Long id) {
        tempQuery = USERS_PRIORITY_TIME_QUERY;
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID,new SchedulePriorityTimePointExtractor(),id);
    }

    private Set<User> getUsersFinalInTimePoint(Long timeID){

        return this.getJdbcTemplate().queryWithParameters(tempQuery, resultSet ->
        { Set<User> set = new HashSet<>();
            do {
                set.add(new UserProxy(resultSet.getLong("id")));
            } while (resultSet.next());
            return set;
        },timeID);
    }
    private class SchedulePriorityTimePointExtractor implements ResultSetExtractor<ScheduleTimePoint> {
        @Override
        public ScheduleTimePoint extractData(ResultSet resultSet) throws SQLException {
            ScheduleTimePoint scheduleTimePoint = new ScheduleTimePointImpl();
            scheduleTimePoint.setId(resultSet.getLong("id"));
            scheduleTimePoint.setTimePoint(resultSet.getTimestamp("tp"));
            scheduleTimePoint.setUsers(getUsersFinalInTimePoint(resultSet.getLong("id")));
            return scheduleTimePoint;
        }
    }


}
