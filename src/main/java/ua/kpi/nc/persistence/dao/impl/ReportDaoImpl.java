package ua.kpi.nc.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.ReportDao;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;
import ua.kpi.nc.report.Line;
import ua.kpi.nc.report.Report;

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
	public ReportInfo getByID(Long id) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for report with id = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ReportInfoExtractor(), id);
	}

	@Override
	public ReportInfo getByTitle(String title) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for report with title = " + title);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, new ReportInfoExtractor(), title);
	}

	@Override
	public Set<ReportInfo> getAll() {
		if (log.isTraceEnabled()) {
			log.trace("Get all reports");
		}
		return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, new ReportInfoExtractor());
	}

	@Override
	public Long insertReport(ReportInfo report) {
		if (log.isTraceEnabled()) {
			log.trace("Inserting report with title = " + report.getTitle());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, report.getQuery(), report.getTitle());
	}

	@Override
	public int updateReport(ReportInfo report) {
		if (log.isTraceEnabled()) {
			log.trace("Updating report with id = " + report.getId());
		}
		return this.getJdbcTemplate().update(SQL_UPDATE, report.getQuery(), report.getTitle(), report.getId());

	}

	@Override
	public int deleteReport(ReportInfo report) {
		if (log.isTraceEnabled()) {
			log.trace("Delete report with id = " + report.getId());
		}
		return this.getJdbcTemplate().update(SQL_DELETE, report.getId());
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
	public Report getReportById(Long id) {
		return null;
	}

	@Override
	public Report getReportOfApproved() {
		ReportInfo reportInfo = getByID(1L);
		Report report = getJdbcTemplate().queryWithParameters(reportInfo.getQuery(), new ReportOfApprovedExtractor());
		if (report != null) {
			report.setTitle(reportInfo.getTitle());
		}
		return report;
	}

	private final class ReportOfApprovedExtractor implements ResultSetExtractor<Report> {

		@Override
		public Report extractData(ResultSet resultSet) throws SQLException {
			Report report = new Report();
			Line header = new Line();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				header.addCell(metaData.getColumnName(i));
			}
			report.setHeader(header);
			do {
				Line line = new Line();
				for (int i = 1; i <= columnCount; i++) {
					line.addCell(resultSet.getObject(i));
				}
				report.addRow(line);
			} while (resultSet.next());

			return report;
		}

	}

	@Override
	public Report getReportOfAnswers(FormQuestion question, List<Recruitment> recruitments) {
		ReportInfo reportInfo = getByID(2L);

		if (recruitments.size() > 0) {
			Recruitment firstRecruitment = recruitments.get(0);
			Report report = getJdbcTemplate().queryWithParameters(reportInfo.getQuery(),
					new ResultSetExtractor<Report>() {

						@Override
						public Report extractData(ResultSet resultSet) throws SQLException {
							Report report = new Report();
							Line header = new Line();
							header.addCell("Recruitment");
							Line firstLine = new Line();
							firstLine.addCell(firstRecruitment.getName());
							do {
								header.addCell(resultSet.getObject(1));
								firstLine.addCell(resultSet.getObject(2));
							} while (resultSet.next());
							report.setHeader(header);
							report.addRow(firstLine);
							return report;
						}
					}, firstRecruitment.getId(), question.getId());
			if (report != null) {
				for (int i = 1; i < recruitments.size(); i++) {
					Line line = (getJdbcTemplate().queryWithParameters(reportInfo.getQuery(),
							new ReportOfAnswerVariantsExtractor(), recruitments.get(i).getId(), question.getId()));
					line.addFirstCell(recruitments.get(i).getName());
					report.addRow(line);
				}
				report.setTitle(reportInfo.getTitle());
				return report;
			}
		}
		return null;
	}

	private final class ReportOfAnswerVariantsExtractor implements ResultSetExtractor<Line> {

		@Override
		public Line extractData(ResultSet resultSet) throws SQLException {
			Line line = new Line();
			do {
				line.addCell(resultSet.getObject(2));
			} while (resultSet.next());
			return line;
		}

	}

}
