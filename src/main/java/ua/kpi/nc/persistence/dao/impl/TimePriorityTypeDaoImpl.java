package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.TimePriorityTypeDao;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Korzh
 */
public class TimePriorityTypeDaoImpl extends JdbcDaoSupport implements TimePriorityTypeDao {
    private static Logger log = LoggerFactory.getLogger(EmailTemplateDaoImpl.class.getName());
    private static final String GET_BY_ID = "SELECT t.id, t.choice FROM public.time_priority_type t WHERE t.id = ?;";
    private static final String GET_BY_PRIORITY = "SELECT t.id, t.choice FROM public.time_priority_type t WHERE t.choice = ?;";

    public TimePriorityTypeDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public TimePriorityType getByID(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form Time Priority type with id  = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID, new TimePriorityTypeExtractor(), id);
    }

    @Override
    public TimePriorityType getByPriority(String choice) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form Time Priority type with priority  = " + choice);
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_PRIORITY, new TimePriorityTypeExtractor(), choice);
    }

    private static final class TimePriorityTypeExtractor implements ResultSetExtractor<TimePriorityType> {
        @Override
        public TimePriorityType extractData(ResultSet resultSet) throws SQLException {
            TimePriorityType timePriorityType = new TimePriorityType();
            timePriorityType.setId(resultSet.getLong("id"));
            timePriorityType.setPriority(resultSet.getString("choice"));
            return timePriorityType;
        }
    }

}
