package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;

import java.util.Set;

/**
 * @author Korzh
 */
public interface ScheduleTimePointDao {

    ScheduleTimePoint getFinalTimePointById(Long id);

    Set<ScheduleTimePoint> getScheduleTimePointByUserId(Long id);

    Long insertScheduleTimePoint(ScheduleTimePointDao scheduleTimePointDao);

    int updateScheduleTimePoint(ScheduleTimePointDao scheduleTimePointDao);

    int deleteScheduleTimePoint(ScheduleTimePointDao scheduleTimePointDao);

}
