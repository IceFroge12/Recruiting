package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.dto.UserTimePriorityDto;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;

import java.util.List;
import java.util.Set;

/**
 * @author Korzh
 */
public interface UserTimePriorityDao {

    UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint);

    Long insertUserPriority(UserTimePriority userTimePriority);

    int updateUserPriority(UserTimePriority userTimePriority);

    int deleteUserPriority(UserTimePriority userTimePriority);

    List<UserTimePriority> getAllUserTimePriorities(Long userId);

	boolean isSchedulePrioritiesExist();

    List<UserTimePriorityDto> getAllTimePriorityForUserById(Long userId);
}
