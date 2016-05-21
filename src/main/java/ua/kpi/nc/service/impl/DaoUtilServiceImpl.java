package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.DaoUtil;
import ua.kpi.nc.service.DaoUtilService;

import javax.sql.DataSource;

/**
 * Created by IO on 21.05.2016.
 */
public class DaoUtilServiceImpl implements DaoUtilService {

    private DaoUtil daoUtil;

    public DaoUtilServiceImpl(DaoUtil daoUtil) {
        this.daoUtil = daoUtil;
    }

    @Override
    public boolean connectionTest() {
        return daoUtil.checkConnection();
    }
}
