package ua.kpi.nc.service;

import java.util.List;

import ua.kpi.nc.persistence.model.TimePriorityType;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface TimePriorityTypeService {
    TimePriorityType getByID(Long id);

    TimePriorityType getByPriority(String priority);

	List<TimePriorityType> getAll();
}
