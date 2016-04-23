package ua.kpi.nc.persistence.dao.impl;

import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.util.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Chalienko on 23.04.2016.
 */
public class FormAnswerDaoImpl extends JdbcDaoSupport implements FormAnswerDao {
    public FormAnswerDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public FormAnswer getById(Long id) {
        return null;
    }
}
