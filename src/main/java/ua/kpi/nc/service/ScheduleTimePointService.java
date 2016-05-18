package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author Korzh
 */
public interface ScheduleTimePointService {

    ScheduleTimePoint getScheduleTimePointById(Long id);

    List<ScheduleTimePoint> getFinalTimePointByUserId(Long id);

    Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

	ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp);

	List<ScheduleTimePoint> getAll();

	boolean isScheduleDatesExists();
	
	boolean isScheduleExists();

    public Map<Long,Long> getUsersNumberInFinalTimePoint(Timestamp timePoint);
}
