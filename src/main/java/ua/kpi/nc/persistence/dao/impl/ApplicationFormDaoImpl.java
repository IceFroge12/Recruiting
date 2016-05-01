package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.proxy.*;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFormDaoImpl extends JdbcDaoSupport implements ApplicationFormDao {

	static final String ID_COL = "id";
	static final String ID_STATUS_COL = "id_status";
	static final String IS_ACTIVE_COL = "is_active";
	static final String ID_RECRUITMENT_COL = "id_recruitment";
	static final String PHOTO_SCOPE_COL = "photo_scope";
	static final String ID_USER_COL = "id_user";
	static final String DATE_CREATE_COL = "date_create";
	
	static final String TABLE_NAME = "application_form";

	private static final String SQL_GET_BY_ID = "SELECT a." + ID_COL + ", a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
			+ ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", s.title \n"
			+ "FROM \"" + TABLE_NAME + "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a.id = ?;";
	private static final String SQL_GET_BY_USER_ID = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
			+ ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
			+ ", s.title \n" + "FROM \"" + TABLE_NAME + "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a."
			+ ID_USER_COL + " = ?;";
	private static final String SQL_GET_BY_STATUS = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
			+ IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", s.title \n" + "FROM \"" + TABLE_NAME + "\" a INNER JOIN status s ON s.id = a."
			+ ID_STATUS_COL + "\n" + "WHERE s.title = ?;";
	private static final String SQL_GET_BY_STATE = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
			+ IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", s.title \n" + "FROM \"" + TABLE_NAME + "\" a INNER JOIN status s ON s.id = a."
			+ ID_STATUS_COL + "\n" + "WHERE a.is_active = ?;";
	private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " a WHERE a." + ID_COL + " = ?;";
	private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ID_STATUS_COL + ", " + IS_ACTIVE_COL
			+ ", " + ID_RECRUITMENT_COL + ", " + PHOTO_SCOPE_COL + ", " + "" + ID_USER_COL + ", " + DATE_CREATE_COL
			+ ") VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SQL_GET_ALL = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
			+ ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", s.title \n" + "FROM \"" + TABLE_NAME + "\" a INNER JOIN status s ON s.id = a."
			+ ID_STATUS_COL + " \n;";
	private static final String SQL_UPDATE = "UPDATE \"" + TABLE_NAME + "\" SET " + ID_STATUS_COL + " = ?, "
			+ IS_ACTIVE_COL + "  = ?, " + PHOTO_SCOPE_COL + " = ?, " + DATE_CREATE_COL + " = ? " + "WHERE " + ID_COL
			+ " = ?";
	private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());

	public ApplicationFormDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public ApplicationForm getById(Long id) {
		if (log.isInfoEnabled()) {
			log.info("Looking for application form with id = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ApplicationFormExtractor(), id);
	}

	@Override
	public List<ApplicationForm> getByUserId(Long id) {
		if (log.isInfoEnabled()) {
			log.info("Looking for application forms of user with id = " + id);
		}
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_USER_ID, new ApplicationFormExtractor(), id);
	}

	@Override
	public List<ApplicationForm> getByStatus(String status) {
		if (log.isInfoEnabled()) {
			log.info("Looking for application forms with status = " + status);
		}
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_STATUS, new ApplicationFormExtractor(), status);
	}

	@Override
	public List<ApplicationForm> getByState(boolean state) {
		if (log.isInfoEnabled()) {
			log.info("Looking for application forms with is_active = " + state);
		}
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_STATE, new ApplicationFormExtractor(), state);
	}

	@Override
	public int deleteApplicationForm(ApplicationForm applicationForm) {
		if (log.isInfoEnabled()) {
			log.info("Deleting application form with id = " + applicationForm.getId());
		}
		return this.getJdbcTemplate().update(SQL_DELETE, applicationForm.getId());
	}

	@Override
	public Long insertApplicationForm(ApplicationForm applicationForm, Connection connection) {
		if (log.isInfoEnabled()) {
			log.info("Inserting application forms with user_id = " + applicationForm.getUser().getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, connection, applicationForm.getStatus().getId(),
				applicationForm.isActive(), applicationForm.getRecruitment().getId(), applicationForm.getPhotoScope(),
				applicationForm.getUser().getId(), applicationForm.getDateCreate());
	}

	@Override
	public int updateApplicationForm(ApplicationForm applicationForm) {
		if (log.isInfoEnabled()) {
			log.info("Updating application forms with id = " + applicationForm.getId());
		}
		return this.getJdbcTemplate().update(SQL_UPDATE, applicationForm.getStatus().getId(),
				applicationForm.isActive(), applicationForm.getPhotoScope(), applicationForm.getDateCreate(),
				applicationForm.getId());
	}

	@Override
	public List<ApplicationForm> getAll() {
		if (log.isInfoEnabled()) {
			log.info("Get all application forms");
		}
		return this.getJdbcTemplate().queryForList(SQL_GET_ALL, new ApplicationFormExtractor());
	}

	private static final String SQL_GET_INTERVIEWS = "SELECT i.id\n" + "FROM \"interview\" i\n"
			+ "WHERE i.id_application_form = ?";

	private List<Interview> getInterviews(Long applicationFormId) {
		return this.getJdbcTemplate().queryForList(SQL_GET_INTERVIEWS, new ResultSetExtractor<Interview>() {

			@Override
			public Interview extractData(ResultSet resultSet) throws SQLException {
				InterviewProxy interviewProxy = new InterviewProxy(resultSet.getLong("id"));
				return interviewProxy;
			}
		}, applicationFormId);
	}

	private static final String SQL_GET_ANSWERS = "SELECT fa.id\n FROM \"form_answer\" fa\n WHERE fa.id_application_form = ?;";

	private List<FormAnswer> getAnswers(Long applicationFormId) {
		return this.getJdbcTemplate().queryForList(SQL_GET_ANSWERS, new ResultSetExtractor<FormAnswer>() {

			@Override
			public FormAnswer extractData(ResultSet resultSet) throws SQLException {
				FormAnswer formAnswerProxy = new FormAnswerProxy(resultSet.getLong("id"));
				return formAnswerProxy;
			}
		}, applicationFormId);
	}

	private List<FormQuestion> getQuestions(Long applicationFormId) {
		return this.getJdbcTemplate().queryWithParameters("SELECT distinct q.id from form_question q join form_answer " +
				"a on (q.id= a.id_question) where a.id_application_form = ?;",
				resultSet -> {
					List<FormQuestion> questions = new ArrayList<>();
					do {
						questions.add(new FormQuestionProxy(resultSet.getLong(FormQuestionDaoImpl.ID_COL)));
					} while (resultSet.next());
					return questions;
				}, applicationFormId);
	}
	private final class ApplicationFormExtractor implements ResultSetExtractor<ApplicationForm> {

		@Override
		public ApplicationForm extractData(ResultSet resultSet) throws SQLException {
			ApplicationForm applicationForm = new ApplicationFormImpl();
			long id = resultSet.getLong(ID_COL);
			applicationForm.setActive(resultSet.getBoolean(IS_ACTIVE_COL));
			applicationForm.setAnswers(getAnswers(id));
			applicationForm.setDateCreate(resultSet.getTimestamp(DATE_CREATE_COL));
			applicationForm.setId(id);
			applicationForm.setRecruitment(new RecruitmentProxy(resultSet.getLong(ID_RECRUITMENT_COL)));
			applicationForm.setInterviews(getInterviews(id));
			applicationForm.setPhotoScope(resultSet.getString(PHOTO_SCOPE_COL));
			applicationForm.setStatus(new Status(resultSet.getLong(ID_STATUS_COL), resultSet.getString("title")));
			applicationForm.setUser(new UserProxy(resultSet.getLong(ID_USER_COL)));
			applicationForm.setQuestions(getQuestions(id));
			return applicationForm;
		}

	}

}
