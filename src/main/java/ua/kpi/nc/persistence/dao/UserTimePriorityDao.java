package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;

import java.util.Set;

/**
 * @author Korzh
 */
public interface UserTimePriorityDao {

    UserTimePriority getByUserPriority(User user, ScheduleTimePoint scheduleTimePoint);

    Long insertUserPriority(UserTimePriority userTimePriority);

    int updateUserPriority(UserTimePriority userTimePriority);

    int deleteUserPriority(UserTimePriority userTimePriority);

    Set<UserTimePriority> getAllTimePoints( );
}
