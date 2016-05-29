package ua.kpi.nc.persistence.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.StatusDao;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IO on 21.04.2016.
 */
public class StatusDaoImpl implements StatusDao {

    private final JdbcDaoSupport jdbcDaoSupport;

    private static Logger log = LoggerFactory.getLogger(StatusDaoImpl.class.getName());

    public StatusDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private ResultSetExtractor<Status> extractor = resultSet -> {
        Status status = new Status();
        status.setId(resultSet.getLong("id"));
        status.setTitle(resultSet.getString("title"));
        return status;
    };


    @Override
    public Status getById(Long id) {
        log.info("Looking for user with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("SELECT status.id, status.title FROM public.status WHERE status.id = ?;",
                extractor, id);
    }

    @Override
    public List<Status> getAllStatuses() {
        log.info("Looking for all statuses");
        return jdbcDaoSupport.getJdbcTemplate().queryForList("SELECT s.id, s.title FROM \"status\" s", extractor);
    }

    @Override
    public int insertStatus(Status status) {
        log.info("Insert status");
        return jdbcDaoSupport.getJdbcTemplate().update("INSERT INTO public.status(status.title) VALUES(?);", status.getTitle());
    }

    @Override
    public int updateStatus(Status status) {
        log.info("Update status with id = ", status.getId());
        return jdbcDaoSupport.getJdbcTemplate().update("UPDATE public.status SET status.title = ? WHERE status.id = ?;",
                status.getTitle(), status.getId());
    }

    @Override
    public int deleteStatus(Status status) {
        log.info("Delete status with id = ", status.getId());
        return jdbcDaoSupport.getJdbcTemplate().update("DELETE FROM public.status WHERE status.id = ?;", status.getId());
    }

    @Override
    public Status getByName(String name) {
        log.info("Looking for user with name = ", name);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("SELECT status.id, status.title FROM public.status WHERE status.title = ?;", extractor, name);
    }
}
