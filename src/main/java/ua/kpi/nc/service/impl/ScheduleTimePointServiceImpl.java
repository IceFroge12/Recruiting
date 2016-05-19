package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.ScheduleTimePointDao;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ScheduleTimePointService;

import java.sql.Timestamp;
import java.util.List;
import java.util.List;
import java.util.Map;

/**
 * @author Korzh
 */
public class ScheduleTimePointServiceImpl implements ScheduleTimePointService {
	private ScheduleTimePointDao scheduleTimePointDao;

	public ScheduleTimePointServiceImpl(ScheduleTimePointDao scheduleTimePointDao) {
		this.scheduleTimePointDao = scheduleTimePointDao;
	}

	@Override
	public ScheduleTimePoint getScheduleTimePointById(Long id) {
		return scheduleTimePointDao.getFinalTimePointById(id);
	}

	@Override
	public List<ScheduleTimePoint> getFinalTimePointByUserId(Long id) {
		return scheduleTimePointDao.getFinalTimePointByUserId(id);
	}

	@Override
	public Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		return scheduleTimePointDao.insertScheduleTimePoint(scheduleTimePoint);
	}

	@Override
	public int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		return scheduleTimePointDao.updateScheduleTimePoint(scheduleTimePoint);
	}

	@Override
	public int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		return scheduleTimePointDao.deleteScheduleTimePoint(scheduleTimePoint);
	}


	@Override
	public int deleteUserTimeFinal(User user, ScheduleTimePoint scheduleTimePoint) {
		return scheduleTimePointDao.deleteUserTimeFinal(user, scheduleTimePoint);
	}

	@Override
	public ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp) {
		return scheduleTimePointDao.getScheduleTimePointByTimepoint(timestamp);
	}

	@Override
	public List<ScheduleTimePoint> getAll() {
		return scheduleTimePointDao.getAll();
	}

	@Override
	public boolean isScheduleDatesExists() {
		return scheduleTimePointDao.isScheduleDatesExists();
	}

	@Override
	public boolean isScheduleExists() {
		return scheduleTimePointDao.isScheduleExists();
	}

	@Override
	public Map<Long,Long> getUsersNumberInFinalTimePoint(Timestamp timePoint) {
		return scheduleTimePointDao.getUsersNumberInFinalTimePoint(timePoint);
	}
}
