package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Korzh
 */
public interface ScheduleTimePointDao {

    ScheduleTimePoint getFinalTimePointById(Long id);

    List<ScheduleTimePoint> getFinalTimePointByUserId(Long id);

    Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteUserTimeFinal(User user, ScheduleTimePoint scheduleTimePoint);

	ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp);

	List<ScheduleTimePoint> getAll();

	boolean isScheduleExists();

	boolean isScheduleDatesExists();

    Map<Long,Long> getUsersNumberInFinalTimePoint(Timestamp timePoint);

    Long addUserToTimepoint(User user, ScheduleTimePoint timePoint);
}
