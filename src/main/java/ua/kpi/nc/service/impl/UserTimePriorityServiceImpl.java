package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.UserTimePriorityDao;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.service.UserTimePriorityService;

import java.util.Set;

/**
 * @author Korzh
 */
public class UserTimePriorityServiceImpl implements UserTimePriorityService{
private UserTimePriorityDao userTimePriorityDao;

    public UserTimePriorityServiceImpl(UserTimePriorityDao userTimePriorityDao) {
        this.userTimePriorityDao = userTimePriorityDao;
    }

    @Override
    public UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint) {
        return userTimePriorityDao.getByUserTime(user,scheduleTimePoint);
    }

    @Override
    public Long insertUserPriority(UserTimePriority userTimePriority) {
        return userTimePriorityDao.insertUserPriority(userTimePriority);
    }

    @Override
    public int updateUserPriority(UserTimePriority userTimePriority) {
        return userTimePriorityDao.updateUserPriority(userTimePriority);
    }

    @Override
    public int deleteUserPriority(UserTimePriority userTimePriority) {
        return userTimePriorityDao.deleteUserPriority(userTimePriority);
    }

    @Override
    public Set<UserTimePriority> getAllUserTimePriorities() {
        return userTimePriorityDao.getAllUserTimePriorities();
    }
}
