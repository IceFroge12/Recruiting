package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ReportDao;
import ua.kpi.nc.persistence.model.Report;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public class ReportDaoImpl extends JdbcDaoSupport implements ReportDao {

    private static final String SQL_GET_BY_ID = "SELECT r.id, r.query, r.title \n FROM report r\n WHERE r.id = ?";
    private static final String SQL_GET_BY_TITLE = "SELECT r.id, r.query, r.title \n FROM report r\n WHERE r.title = ?";
    private static final String SQL_GET_ALL = "SELECT r.id, r.query, r.title \n FROM report r";
    private static final String SQL_INSERT = "INSERT INTO report (query, title) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE report SET query = ? , title= ? WHERE report.id = ?";
    private static final String SQL_DELETE = "DELETE FROM report WHERE report.id = ?";
    private static Logger log = LoggerFactory.getLogger(ReportDaoImpl.class.getName());

    public ReportDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Report getByID(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for report with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ReportExtractor(), id);
    }

    @Override
    public Report getByTitle(String title) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for report with title = " + title);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, new ReportExtractor(), title);
    }

    @Override
    public Set<Report> getAll() {
        if (log.isTraceEnabled()) {
            log.trace("Get all reports");
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, new ReportExtractor());
    }

    @Override
    public Long insertReport(Report report) {
        if (log.isTraceEnabled()) {
            log.trace("Inserting report with title = " + report.getTitle());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, report.getTitle());
    }

    @Override
    public int updateReport(Report report) {
        if (log.isTraceEnabled()) {
            log.trace("Updating report with id = " + report.getId());
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, report.getQuery(), report.getTitle(), report.getId());

    }

    @Override
    public int deleteReport(Report report) {
        if (log.isTraceEnabled()) {
            log.trace("Delete report with id = " + report.getId());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, report.getId());
    }
    private final class ReportExtractor implements ResultSetExtractor<Report> {

        @Override
        public Report extractData(ResultSet resultSet) throws SQLException {
            Report report = new Report();
            long reportId = resultSet.getLong("id");
            report.setId(reportId);
            report.setQuery(resultSet.getString("query"));
            report.setTitle(resultSet.getString("title"));
            return report;
        }

    }
}
