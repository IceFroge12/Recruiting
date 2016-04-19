package ua.kpi.nc.domain.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * Created by Chalienko on 13.04.2016.
 */

public abstract class DaoSupport {

    @Autowired
    protected DataSource dataSource;

    public DataSource getDataSource(){
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
