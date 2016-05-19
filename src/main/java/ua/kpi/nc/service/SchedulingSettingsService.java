package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.SchedulingSettings;

import java.util.List;

/**
 * @author Korzh
 */
public interface SchedulingSettingsService {

    SchedulingSettings getById(Long id);

    int deleteAll();

    Long insertTimeRange(SchedulingSettings schedulingSettings);

    int updateTimeRange(SchedulingSettings schedulingSettings);

    int deleteTimeRange(Long id);

    List<SchedulingSettings> getAll();
}
