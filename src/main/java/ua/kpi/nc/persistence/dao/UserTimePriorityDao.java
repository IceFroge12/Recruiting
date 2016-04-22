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

    Long insertUserPriority(User user, ScheduleTimePoint scheduleTimePoint);

    int updateUserPriority(User user, ScheduleTimePoint scheduleTimePoint);

    int deleteUserPriority(User user, ScheduleTimePoint scheduleTimePoint);

    Set<UserTimePriority> getAllTimePoints( );
}
