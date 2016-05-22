package ua.kpi.nc.persistence.model.impl.proxy;

import java.sql.Timestamp;
import java.util.Set;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.real.ScheduleTimePointImpl;
import ua.kpi.nc.service.ScheduleTimePointService;
import ua.kpi.nc.service.ServiceFactory;

public class ScheduleTimePointProxy implements ScheduleTimePoint {

    private static final long serialVersionUID = -3918415375041281959L;

    private Long id;

    private ScheduleTimePointImpl scheduleTimePoint;

    private ScheduleTimePointService service;

    public ScheduleTimePointProxy() {
    }

    public ScheduleTimePointProxy(Long id) {
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
        checkScheduleTimePoit();
        return scheduleTimePoint.getTimePoint();
    }

    @Override
    public void setTimePoint(Timestamp timePoint) {
        checkScheduleTimePoit();
        scheduleTimePoint.setTimePoint(timePoint);
    }

    @Override
    public Set<User> getUsers() {
        checkScheduleTimePoit();
        return scheduleTimePoint.getUsers();
    }

    @Override
    public void setUsers(Set<User> users) {
        checkScheduleTimePoit();
        scheduleTimePoint.setUsers(users);
    }

    @Override
    public Set<UserTimePriority> getUserTimePriorities() {
        checkScheduleTimePoit();
        return scheduleTimePoint.getUserTimePriorities();
    }

    @Override
    public void setUserTimePriorities(Set<UserTimePriority> priorities) {
        checkScheduleTimePoit();
        scheduleTimePoint.setUserTimePriorities(priorities);
    }

    private void checkScheduleTimePoit(){
        if (scheduleTimePoint == null) {
            service = ServiceFactory.getScheduleTimePointService();
            scheduleTimePoint = downloadTimePoint();
        }
    }

    private ScheduleTimePointImpl downloadTimePoint() {
        return (ScheduleTimePointImpl) service.getScheduleTimePointById(id);
    }

}
