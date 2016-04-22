package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;

/**
 * @author Korzh
 */
public interface ScheduleTimePointDao {

    ScheduleTimePoint getFinalTimePointById(Long id);

    ScheduleTimePoint getPriorityTimeById(Long id);



}
