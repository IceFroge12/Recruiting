package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.SchedulingSettings;

import java.util.List;

/**
 * @author Korzh
 */
public interface SchedulingSettingsDao {

    SchedulingSettings getById(Long id);

    int deleteAll();

    Long insertTimeRange(SchedulingSettings schedulingSettings);

    int updateTimeRange(SchedulingSettings schedulingSettings);

    int deleteTimeRange(Long id);

    List<SchedulingSettings> getAll();
 }
