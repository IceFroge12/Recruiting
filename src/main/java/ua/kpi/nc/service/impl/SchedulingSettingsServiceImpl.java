package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.SchedulingSettingsDao;
import ua.kpi.nc.persistence.model.SchedulingSettings;
import ua.kpi.nc.service.ScheduleTimePointService;
import ua.kpi.nc.service.SchedulingSettingsService;

import java.util.List;

/**
 * @author Korzh
 */
public class SchedulingSettingsServiceImpl implements SchedulingSettingsService {

    private SchedulingSettingsDao schedulingSettingsDao;

    public SchedulingSettingsServiceImpl(SchedulingSettingsDao schedulingSettingsDao) {
        this.schedulingSettingsDao = schedulingSettingsDao;
    }

    @Override
    public int deleteAll(){
        return schedulingSettingsDao.deleteAll();
    }

    @Override
    public SchedulingSettings getById(Long id) {
        return schedulingSettingsDao.getById(id);
    }

    @Override
    public Long insertTimeRange(SchedulingSettings schedulingSettings) {
        return schedulingSettingsDao.insertTimeRange(schedulingSettings);
    }

    @Override
    public int updateTimeRange(SchedulingSettings schedulingSettings) {
        return schedulingSettingsDao.updateTimeRange(schedulingSettings);
    }

    @Override
    public int deleteTimeRange(Long id) {
        return schedulingSettingsDao.deleteTimeRange(id);
    }

    @Override
    public List<SchedulingSettings> getAll() {
        return schedulingSettingsDao.getAll();
    }
}
