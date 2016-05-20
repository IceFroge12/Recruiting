package ua.kpi.nc.service;

import ua.kpi.nc.persistence.dto.UserTimePriorityDto;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;

import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface UserTimePriorityService {

    UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint);

    Long insertUserPriority(UserTimePriority userTimePriority);

    int updateUserPriority(UserTimePriority userTimePriority);

    int deleteUserPriority(UserTimePriority userTimePriority);

    List<UserTimePriority> getAllUserTimePriorities(Long userId);

    int[] batchUpdateUserPriority(List<UserTimePriority> userTimePriorities);

    int[] batchCreateUserPriority(List<UserTimePriority> userTimePriorities);

    void createStudentTimePriotities(User student);

    boolean isSchedulePrioritiesExistStaff();
    
    boolean isSchedulePrioritiesExistStudent();

    public List<UserTimePriorityDto> getAllTimePriorityForUserById(Long userId);

}
