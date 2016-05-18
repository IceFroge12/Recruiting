package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.SchedulingSettingsDao;
import ua.kpi.nc.persistence.model.SchedulingSettings;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Korzh
 */
public class SchedulingSettingsDaoImpl extends JdbcDaoSupport implements SchedulingSettingsDao {

    private static Logger log = LoggerFactory.getLogger(SchedulingSettingsDaoImpl.class.getName());

    private ResultSetExtractor<SchedulingSettings> extractor = resultSet -> {
        SchedulingSettings schedulingSettings = new SchedulingSettings();
        schedulingSettings.setId(resultSet.getLong("id"));
        schedulingSettings.setStartDate(resultSet.getTimestamp("start_time"));
        schedulingSettings.setEndDate(resultSet.getTimestamp("end_time"));
        return schedulingSettings;
    };

    public SchedulingSettingsDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private static final String GET_BY_ID = "select ss.id, ss.start_time, ss.end_time from scheduling_settings ss where id = ?;";
    private static final String INSERT_TIME_RANGE = "INSERT INTO scheduling_settings (start_time, end_time) VALUES (?,?);";
    private static final String UPDATE_TIME_RANGE = "UPDATE scheduling_settings set start_time = ?, end_time = ? WHERE id = ?;";
    private static final String DELETE_TIME_RANGE = "DELETE FROM scheduling_settings WHERE id = ?;";
    private static final String GET_ALL = "SELECT  ss.id, ss.start_time, ss.end_time FROM scheduling_settings ss ORDER BY start_time;";

    @Override
    public SchedulingSettings getById(Long id) {
        log.trace("Looking for Scheduling Setting with id = {} = ", id);
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
    }

    @Override
    public Long insertTimeRange(SchedulingSettings schedulingSettings) {
        log.trace("Inserting Scheduling Setting with startDate = {} ", schedulingSettings.getStartDate());
        return this.getJdbcTemplate().insert(INSERT_TIME_RANGE, schedulingSettings.getStartDate(),
                schedulingSettings.getEndDate());
    }

    @Override
    public int updateTimeRange(SchedulingSettings schedulingSettings) {
        log.trace("Updating Scheduling Setting with id = {} ", schedulingSettings.getId());
        return this.getJdbcTemplate().update(UPDATE_TIME_RANGE, schedulingSettings.getStartDate(), schedulingSettings.getEndDate(), schedulingSettings.getId());
    }

    @Override
    public int deleteTimeRange(Long id) {
        log.trace("Delete Scheduling Setting with id = {}", id);
        return this.getJdbcTemplate().update(DELETE_TIME_RANGE, id);
    }

    @Override
    public List<SchedulingSettings> getAll() {
        log.trace("Getting All of Scheduling Settings ");
        return this.getJdbcTemplate().queryForList(GET_ALL, extractor);
    }
}
