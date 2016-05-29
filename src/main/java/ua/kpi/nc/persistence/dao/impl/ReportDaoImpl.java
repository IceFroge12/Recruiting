package ua.kpi.nc.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.ReportDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;
import ua.kpi.nc.report.Line;

/**
 * Created by Nikita on 24.04.2016.
 */
public class ReportDaoImpl implements ReportDao {

    private final JdbcDaoSupport jdbcDaoSupport;

    private static final String SQL_GET_BY_ID = "SELECT r.id, r.query, r.title \n FROM report r\n WHERE r.id = ?";
    private static final String SQL_GET_BY_TITLE = "SELECT r.id, r.query, r.title \n FROM report r\n WHERE r.title = ?";
    private static final String SQL_GET_ALL = "SELECT r.id, r.query, r.title \n FROM report r";
    private static final String SQL_INSERT = "INSERT INTO report (query, title) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE report SET query = ? , title= ? WHERE report.id = ?";
    private static final String SQL_DELETE = "DELETE FROM report WHERE report.id = ?";
    private static Logger log = LoggerFactory.getLogger(ReportDaoImpl.class.getName());

    public ReportDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public ReportInfo getByID(Long id) {
        log.info("Looking for report with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ReportInfoExtractor(), id);
    }

    @Override
    public ReportInfo getByTitle(String title) {
        log.info("Looking for report with title = {}", title);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, new ReportInfoExtractor(), title);
    }

    @Override
    public Set<ReportInfo> getAll() {
        log.info("Get all reports");
        return jdbcDaoSupport.getJdbcTemplate().queryForSet(SQL_GET_ALL, new ReportInfoExtractor());
    }

    @Override
    public Long insertReport(ReportInfo report) {
        log.info("Inserting report with title = {}", report.getTitle());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, report.getQuery(), report.getTitle());
    }

    @Override
    public int updateReport(ReportInfo report) {
        log.info("Updating report with id = {}", report.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, report.getQuery(), report.getTitle(), report.getId());

    }

    @Override
    public int deleteReport(ReportInfo report) {
        log.info("Delete report with id = {}", report.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, report.getId());
    }

    private final class ReportInfoExtractor implements ResultSetExtractor<ReportInfo> {

        @Override
        public ReportInfo extractData(ResultSet resultSet) throws SQLException {
            ReportInfo report = new ReportInfo();
            long reportId = resultSet.getLong("id");
            report.setId(reportId);
            report.setQuery(resultSet.getString("query"));
            report.setTitle(resultSet.getString("title"));
            return report;
        }

    }

    @Override
    public List<Line> extractWithMetaData(ReportInfo reportInfo) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(reportInfo.getQuery(), reportWithMetaDataExtractor);
    }

    private final ResultSetExtractor<List<Line>> reportWithMetaDataExtractor = resultSet -> {
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            line.addCell(metaData.getColumnName(i));
        }
        lines.add(line);
        do {
            line = new Line();
            for (int i = 1; i <= columnCount; i++) {
                line.addCell(resultSet.getObject(i));
            }
            lines.add(line);
        } while (resultSet.next());
        return lines;
    };

    private static final String AMOUNT_COL = "amount";

    @Override
    public Line getAnswerVariantLine(ReportInfo reportInfo, FormQuestion question,
                                     FormAnswerVariant formAnswerVariant) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(reportInfo.getQuery(), resultSet -> {
            Line line = new Line();
            do {
                line.addCell(resultSet.getObject(AMOUNT_COL));
            } while (resultSet.next());
            return line;
        }, question.getId(), formAnswerVariant.getId());
    }
}
