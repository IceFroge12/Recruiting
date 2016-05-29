package ua.kpi.nc.persistence.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.TimePriorityTypeDao;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

/**
 * @author Korzh
 */
public class TimePriorityTypeDaoImpl implements TimePriorityTypeDao {

    private final JdbcDaoSupport jdbcDaoSupport;

    private static Logger log = LoggerFactory.getLogger(TimePriorityTypeDaoImpl.class.getName());
    private static final String GET_BY_ID = "SELECT t.id, t.choice FROM public.time_priority_type t WHERE t.id = ?;";
    private static final String GET_ALL = "SELECT t.id, t.choice FROM public.time_priority_type t";
    private static final String GET_BY_PRIORITY = "SELECT t.id, t.choice FROM public.time_priority_type t WHERE t.choice = ?;";

    private ResultSetExtractor<TimePriorityType> extractor = resultSet -> {
        TimePriorityType timePriorityType = new TimePriorityType();
        timePriorityType.setId(resultSet.getLong("id"));
        timePriorityType.setPriority(resultSet.getString("choice"));
        return timePriorityType;
    };

    public TimePriorityTypeDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public TimePriorityType getByID(Long id) {
        log.info("Looking for form Time Priority type with id  = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
    }

    @Override
    public TimePriorityType getByPriority(String choice) {
        log.info("Looking for form Time Priority type with priority  = {}", choice);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_PRIORITY, extractor, choice);
    }

    @Override
    public List<TimePriorityType> getAll() {
        log.info("Get all time priorities");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(GET_ALL, extractor);
    }
}
