package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;

import java.util.Set;

/**
 * @author Korzh
 */
public interface ScheduleTimePointService {

    ScheduleTimePoint getScheduleTimePointById(Long id);

    Set<ScheduleTimePoint> getFinalTimePointByUserId(Long id);

    Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);
}
