package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ScheduleTimePointDao;
import ua.kpi.nc.persistence.util.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Korzh
 */
public class SchedulingPointDaoImpl extends JdbcDaoSupport implements ScheduleTimePointDao{
    private static Logger log = LoggerFactory.getLogger(SchedulingPointDaoImpl.class.getName());

    public SchedulingPointDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }
    private static final String GET_BY_ID = ";";
    @Override
    public ScheduleTimePointDao getById(Long id) {
        return null;
    }
}
