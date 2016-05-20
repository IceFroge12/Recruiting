package ua.kpi.nc.persistence.dao.impl;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.UserTimePriorityDao;
import ua.kpi.nc.persistence.dto.UserTimePriorityDto;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.proxy.ScheduleTimePointProxy;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import java.util.List;

/**
 * @author Korzh
 */
public class UserTimePriorityDaoImpl extends JdbcDaoSupport implements UserTimePriorityDao {
    private static Logger log = LoggerFactory.getLogger(UserTimePriorityDaoImpl.class.getName());

    public UserTimePriorityDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private ResultSetExtractor<UserTimePriority> extractor = resultSet -> {
        UserTimePriority userTimePriority = new UserTimePriority();
        userTimePriority.setUser(new UserProxy(resultSet.getLong("id_user")));
        userTimePriority.setScheduleTimePoint(new ScheduleTimePointProxy(resultSet.getLong("id_time_point")));
        userTimePriority.setTimePriorityType(new TimePriorityType(resultSet.getLong("id_priority_type"), resultSet.getString("choice")));
        return userTimePriority;
    };

    private ResultSetExtractor<UserTimePriorityDto> extractorDto = resultSet -> {
        UserTimePriorityDto userTimePriorityDto = new UserTimePriorityDto();
        userTimePriorityDto.setTimePoint(resultSet.getTimestamp("time_point"));
        userTimePriorityDto.setTimeStampId(resultSet.getLong("id_time_point"));
        userTimePriorityDto.setIdPriorityType(resultSet.getLong("id_priority_type"));
        return userTimePriorityDto;
    };

    private static final String GET_BY_USER_ID_TIME_POINT_ID = "SELECT p.id_user, p.id_time_point, p.id_priority_type, pt.choice " +
            "FROM public.user_time_priority p join public.time_priority_type pt on (p.id_priority_type= pt.id) " +
            "WHERE p.id_user= ? and  p.id_time_point=? ;";
    private static final String INSERT_USER_TIME_PRIORITY = "INSERT INTO user_time_priority (id_user, id_time_point, id_priority_type) VALUES (?,?,?);";
    private static final String UPDATE_USER_TIME_PRIORITY = "UPDATE user_time_priority p set id_priority_type = ? WHERE p.id_user = ? and p.id_time_point = ?;";
    private static final String DELETE_USER_TIME_PRIORITY = "DELETE FROM public.user_time_priority p WHERE p.id_user = ? and p.id_time_point = ?;";
    private static final String GET_ALL_USER_TIME_PRIORITY = "SELECT p.id_user, p.id_time_point, p.id_priority_type, pt.choice " +
            "FROM public.user_time_priority p join public.time_priority_type pt on (p.id_priority_type= pt.id) Where p.id_user = ?;";
    private static final String IS_PRIORITIES_EXIST_FOR_STAFF = "SELECT EXISTS(SELECT 1\n" +
            "              FROM user_time_priority utp\n" +
            "JOIN \"user\" u on utp.id_user = u.id\n" +
            "JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role = 2 OR ur.id_role = 5);";
    private static final String IS_PRIORITIES_EXIST_FOR_STUDENT = "SELECT EXISTS(SELECT 1\n" +
            "              FROM user_time_priority utp\n" +
            "JOIN \"user\" u on utp.id_user = u.id\n" +
            "JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role = 3);";

    private static final String GET_ALL_TIME_PRIORITY_FOR_USER_BY_ID = "SELECT\n" +
            "  p.id_priority_type,\n" +
            "  p.id_time_point,"+
            "  stp.time_point\n" +
            "FROM public.user_time_priority p join public.time_priority_type pt\n" +
            "    on (p.id_priority_type= pt.id) JOIN schedule_time_point stp ON  " +
            "(stp.id = p.id_time_point) Where p.id_user = ? ORDER BY stp.time_point";

    @Override
    public UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint) {
        log.trace("Looking for User time priority  with id user,  id  scheduleTimePoint = ", user.getId()
                , scheduleTimePoint.getId());
        return this.getJdbcTemplate().queryWithParameters(GET_BY_USER_ID_TIME_POINT_ID,
                extractor, user.getId(), scheduleTimePoint.getId());
    }

    @Override
    public Long insertUserPriority(UserTimePriority userTimePriority) {
        log.trace("Inserting User time priority  with id user,  id  scheduleTimePoint = ",
                userTimePriority.getUser().getId(), userTimePriority.getScheduleTimePoint().getId());
        return this.getJdbcTemplate().insert(INSERT_USER_TIME_PRIORITY, userTimePriority.getUser().getId(),
                userTimePriority.getScheduleTimePoint().getId(), userTimePriority.getTimePriorityType().getId());
    }

    @Override
    public int updateUserPriority(UserTimePriority userTimePriority) {
        log.trace("Inserting User time priority  with id user,  id  scheduleTimePoint = {}",
                userTimePriority.getUser().getId(), userTimePriority.getScheduleTimePoint().getId());
        return this.getJdbcTemplate().update(UPDATE_USER_TIME_PRIORITY, userTimePriority.getTimePriorityType().getId(), userTimePriority.getUser().getId(),
                userTimePriority.getScheduleTimePoint().getId());
    }

    @Override
    public int[] batchUpdateUserPriority(List<UserTimePriority> userTimePriorities) {
        log.trace("Inserting User time priority  with id user,  id  scheduleTimePoint = {}");
        Object[][] objects = new Object[userTimePriorities.size()][];
        int count = 0;
        for (UserTimePriority userTimePriority : userTimePriorities){
            objects[count] = new Object[]{
                    userTimePriority.getTimePriorityType().getId(),
                    userTimePriority.getUser().getId(),
                    userTimePriority.getScheduleTimePoint().getId()};
            count++;
        }
        return this.getJdbcTemplate().batchUpdate(UPDATE_USER_TIME_PRIORITY, objects);
    }

    @Override
    public int deleteUserPriority(UserTimePriority userTimePriority) {
        log.trace("Deleting User time priority  with id user,  id  scheduleTimePoint = ",
                userTimePriority.getUser().getId(), userTimePriority.getScheduleTimePoint().getId());
        return this.getJdbcTemplate().update(DELETE_USER_TIME_PRIORITY, userTimePriority.getUser().getId(),
                userTimePriority.getScheduleTimePoint().getId());
    }

    @Override
    public List<UserTimePriority> getAllUserTimePriorities(Long userId) {
        log.trace("Getting all User time priorities ");
        return this.getJdbcTemplate().queryForList(GET_ALL_USER_TIME_PRIORITY, extractor, userId);
    }

    @Override
    public List<UserTimePriorityDto> getAllTimePriorityForUserById(Long userId){
        log.trace("Getting all time priorities for user with id ={} ", userId);
        return this.getJdbcTemplate().queryForList(GET_ALL_TIME_PRIORITY_FOR_USER_BY_ID, extractorDto,userId);
    }

	@Override
	public boolean isSchedulePrioritiesExistStudent() {
		log.trace("Check the existing of user time priorities.");
		return this.getJdbcTemplate().queryWithParameters(IS_PRIORITIES_EXIST_FOR_STUDENT, resultSet -> resultSet.getBoolean(1));
	}
    @Override
    public boolean isSchedulePrioritiesExistStaff() {
        log.trace("Check the existing of user time priorities.");
        return this.getJdbcTemplate().queryWithParameters(IS_PRIORITIES_EXIST_FOR_STAFF, resultSet -> resultSet.getBoolean(1));
    }


}
