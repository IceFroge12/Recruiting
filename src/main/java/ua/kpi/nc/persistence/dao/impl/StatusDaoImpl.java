package ua.kpi.nc.persistence.dao.impl;

import org.apache.log4j.Logger;
import ua.kpi.nc.persistence.dao.StatusDao;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IO on 21.04.2016.
 */
public class StatusDaoImpl extends JdbcDaoSupport implements StatusDao {

    private static Logger log = Logger.getLogger(StatusDaoImpl.class.getName());

    public StatusDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Status getById(Long id) {
        if (log.isTraceEnabled()){
            log.trace("Looking for user with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT status.id, status.title FROM public.status WHERE status.id = ?;", new StatusExtractor(), id);
    }

    @Override
    public int insertStatus(Status status) {
        return this.getJdbcTemplate().update("INSERT INTO public.status(status.title) VALUES(?);", status.getTitle());
    }

    @Override
    public int updateStatus(Status status) {
        return this.getJdbcTemplate().update("UPDATE public.status SET status.title = ? WHERE status.id = ?;", status.getTitle(), status.getId());
    }

    @Override
    public int deleteStatus(Status status) {
        return this.getJdbcTemplate().update("DELETE FROM public.status WHERE status.id = ?;", status.getId());
    }

    private static final class StatusExtractor implements ResultSetExtractor<Status>{

        @Override
        public Status extractData(ResultSet resultSet) throws SQLException {
            Status status = new Status();
            status.setId(resultSet.getLong("id"));
            status.setTitle(resultSet.getString("title"));
            return null;
        }
    }

}
