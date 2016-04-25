package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.StatusDao;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.service.StatusService;

/**
 * Created by Алексей on 23.04.2016.
 */
public class StatusServiceImpl implements StatusService {

    private StatusDao statusDao;

    public StatusServiceImpl(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public Status getStatusById(Long id) {
        return statusDao.getById(id);
    }

    @Override
    public int insertStatus(Status status) {
        return statusDao.insertStatus(status);
    }

    @Override
    public int updateStatus(Status status) {
      return  statusDao.updateStatus(status);
    }

    @Override
    public int deleteStatus(Status status) {
        return statusDao.deleteStatus(status);
    }
}
