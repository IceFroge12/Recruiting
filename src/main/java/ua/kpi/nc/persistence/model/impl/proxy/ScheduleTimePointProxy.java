package ua.kpi.nc.persistence.model.impl.proxy;

import java.sql.Timestamp;
import java.util.Set;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.real.ScheduleTimePointImpl;
import ua.kpi.nc.service.ScheduleTimePointService;

public class ScheduleTimePointProxy implements ScheduleTimePoint {

    private static final long serialVersionUID = 2160249328142111936L;

    private Long id;

    private Timestamp timePoint;

    private Set<User> users;

    private ScheduleTimePointImpl scheduleTimePoint;

    private ScheduleTimePointService service;

    public ScheduleTimePointProxy() {
    }

    public ScheduleTimePointProxy(Long id) {
        super();
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Timestamp getTimePoint() {
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
        return scheduleTimePoint.getTimePoint();
    }

    @Override
    public void setTimePoint(Timestamp timePoint) {
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
        scheduleTimePoint.setTimePoint(timePoint);
    }

    @Override
    public Set<User> getUsers() {
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
        return scheduleTimePoint.getUsers();
    }

    @Override
    public void setUsers(Set<User> users) {
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
        scheduleTimePoint.setUsers(users);
    }

    @Override
    public Set<UserTimePriority> getUserTimePriorities() {
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
        return scheduleTimePoint.getUserTimePriorities();
    }

    @Override
    public void setUserTimePriorities(Set<UserTimePriority> priorities) {
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
        scheduleTimePoint.setUserTimePriorities(priorities);
    }

    private ScheduleTimePointImpl downloadTimePoint() {
        return (ScheduleTimePointImpl) service.getScheduleTimePointById(id);
    }

}
