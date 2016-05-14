package ua.kpi.nc.persistence.dao.impl;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

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

/**
 * @author Korzh
 */
public class ScheduleTimePointDaoImpl extends JdbcDaoSupport implements ScheduleTimePointDao {

	private static Logger log = LoggerFactory.getLogger(ScheduleTimePointDaoImpl.class.getName());

	private ResultSetExtractor<ScheduleTimePoint> extractor = resultSet -> {
		ScheduleTimePoint scheduleTimePoint = new ScheduleTimePointImpl();
		scheduleTimePoint.setId(resultSet.getLong("id"));
		scheduleTimePoint.setTimePoint(resultSet.getTimestamp("tp"));
		scheduleTimePoint.setUserTimePriorities(getUserTimePriority(resultSet.getLong("id"), scheduleTimePoint));
		scheduleTimePoint.setUsers(getUsersFinalInTimePoint(resultSet.getLong("id")));
		return scheduleTimePoint;
	};

	public ScheduleTimePointDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	private static final String GET_BY_ID = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.id = ?;";
	private static final String GET_BY_TIMEPOINT = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.time_point = ?;";
	private static final String GET_ALL = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s";
	private static final String USERS_FINAL_TIME_QUERY = "SELECT u.id, u.email, u.first_name,u.last_name,u.second_name,"
			+ " u.password, u.confirm_token, u.is_active, u.registration_date "
			+ "FROM public.user u join public.user_time_final f on u.id=f.id_user join public.schedule_time_point s on  f.id_time_point= s.id Where s.id=?;";

	private static final String USER_TIME_PRIORITY = "select  utp.id_user, utp.id_time_point, utp.id_priority_type, "
			+ "tpt.choice  from user_time_priority utp inner join time_priority_type tpt on tpt.id = utp.id_priority_type "
			+ "where utp.id_time_point = ?;";

	private static final String FINAL_TIME_POINT_BY_USER_ID = "SELECT s.id, s.time_point tp FROM public.user u "
			+ "join public.user_time_final f on u.id=f.id_user join public.schedule_time_point s on  f.id_time_point= s.id Where u.id=?;";

	private static final String INSERT_SCHEDULE_TIME_POINT = "INSERT INTO schedule_time_point ( time_point) VALUES (?);";

	private static final String UPDATE_SCHEDULE_TIME_POINT = "UPDATE schedule_time_point set time_point = ? WHERE id = ?;";

	private static final String DELETE_SCHEDULE_TIME_POINT = "DELETE FROM schedule_time_point WHERE id = ?";

	private static final String SQL_IS_SCHEDULE_EXISTS = "SELECT EXISTS (SELECT stp.id FROM schedule_time_point stp)";
	
	@Override
	public ScheduleTimePoint getFinalTimePointById(Long id) {
		log.trace("Looking for Schedule time Point with id = ", id);
		return this.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
	}

	@Override
	public List<ScheduleTimePoint> getFinalTimePointByUserId(Long id) {
		log.trace("Looking for Schedule time Point with User_id = ", id);
		return this.getJdbcTemplate().queryForList(FINAL_TIME_POINT_BY_USER_ID, extractor, id);
	}

	@Override
	public Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		log.trace("Inserting Schedule time Point with id = ", scheduleTimePoint.getId());
		return this.getJdbcTemplate().insert(INSERT_SCHEDULE_TIME_POINT, scheduleTimePoint.getTimePoint());
	}

	@Override
	public int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		log.trace("Updating Schedule time Point with id = ", scheduleTimePoint.getId());
		return this.getJdbcTemplate().update(UPDATE_SCHEDULE_TIME_POINT, scheduleTimePoint.getTimePoint(),
				scheduleTimePoint.getId());
	}

	@Override
	public int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		log.trace("Deleting Schedule time Point with id = ", scheduleTimePoint.getId());
		return this.getJdbcTemplate().update(DELETE_SCHEDULE_TIME_POINT, scheduleTimePoint.getId());
	}

	private Set<User> getUsersFinalInTimePoint(Long timeID) {
		return this.getJdbcTemplate().queryWithParameters(USERS_FINAL_TIME_QUERY, resultSet -> {
			Set<User> set = new HashSet<>();
			do {
				set.add(new UserProxy(resultSet.getLong("id")));
			} while (resultSet.next());
			return set;
		}, timeID);
	}

	private Set<UserTimePriority> getUserTimePriority(Long timeID, ScheduleTimePoint scheduleTimePoint) {
		return this.getJdbcTemplate().queryWithParameters(USER_TIME_PRIORITY, resultSet -> {
			Set<UserTimePriority> set = new HashSet<>();
			do {
				set.add(new UserTimePriority(new UserProxy(resultSet.getLong("id_user")), scheduleTimePoint,
						new TimePriorityType(resultSet.getLong("id_priority_type"), resultSet.getString("choice"))));
			} while (resultSet.next());
			return set;
		}, timeID);
	}

	@Override
	public ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp) {
		log.trace("Looking for Schedule time Point with timestamp = ", timestamp);
		return this.getJdbcTemplate().queryWithParameters(GET_BY_TIMEPOINT, extractor, timestamp);
	}

	@Override
	public List<ScheduleTimePoint> getAll() {
		if (log.isTraceEnabled()) {
			log.trace("Looking for all Schedule time Points");
		}
		return this.getJdbcTemplate().queryForList(GET_ALL, extractor);
	}

	@Override
	public boolean isScheduleExists() {
		if (log.isTraceEnabled()) {
			log.trace("Checking the the existence of schedule.");
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_IS_SCHEDULE_EXISTS, resultSet -> resultSet.getBoolean(1));
	}
}
