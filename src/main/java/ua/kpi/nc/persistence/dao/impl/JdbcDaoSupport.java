package ua.kpi.nc.persistence.dao.impl;

import ua.kpi.nc.persistence.util.JdbcTemplate;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class JdbcDaoSupport {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}

