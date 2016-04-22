package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Status;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface StatusDao {
    Status getById(Long id);

    int insertStatus(Status status);

    int updateStatus(Status status);

    int deleteStatus(Status status);

}
