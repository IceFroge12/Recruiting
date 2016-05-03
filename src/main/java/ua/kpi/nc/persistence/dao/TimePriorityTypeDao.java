package ua.kpi.nc.persistence.dao;

import java.util.List;

import ua.kpi.nc.persistence.model.TimePriorityType;

/**
 * @author Korzh
 */
public interface TimePriorityTypeDao {
    TimePriorityType getByID(Long id);

    TimePriorityType getByPriority(String priority);

	List<TimePriorityType> getAll();
}
