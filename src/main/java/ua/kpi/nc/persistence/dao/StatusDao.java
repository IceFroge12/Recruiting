package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Status;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface StatusDao {
    void getById(Long id);

    void getByTitle(String title);

    Status insertStatus(Status status);

    void updateStatus(Long id, Status status);

    void deleteStatus(Status status);

    void deleteStatus(Long id);
}
