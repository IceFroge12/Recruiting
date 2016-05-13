package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Status;

import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface StatusDao {
    Status getById(Long id);

    List<Status> getAllStatuses();

    int insertStatus(Status status);

    int updateStatus(Status status);

    int deleteStatus(Status status);

    Status getByName(String name);

}
