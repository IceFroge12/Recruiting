package ua.kpi.nc.persistence.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.proxy.*;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

public class ApplicationFormDaoImpl extends JdbcDaoSupport implements ApplicationFormDao {

	static final String ID_COL = "id";
	static final String ID_STATUS_COL = "id_status";
	static final String IS_ACTIVE_COL = "is_active";
	static final String ID_RECRUITMENT_COL = "id_recruitment";
	static final String PHOTO_SCOPE_COL = "photo_scope";
	static final String ID_USER_COL = "id_user";
	static final String DATE_CREATE_COL = "date_create";
	static final String FEEDBACK = "feedback";

	static final String TABLE_NAME = "application_form";

	private ResultSetExtractor<ApplicationForm> extractor = resultSet -> {
		ApplicationForm applicationForm = new ApplicationFormImpl();
		long id = resultSet.getLong(ID_COL);
		applicationForm.setActive(resultSet.getBoolean(IS_ACTIVE_COL));
		applicationForm.setAnswers(getAnswers(id));
		applicationForm.setDateCreate(resultSet.getTimestamp(DATE_CREATE_COL));
		applicationForm.setFeedback(resultSet.getString(FEEDBACK));
		applicationForm.setId(id);
		applicationForm.setRecruitment(new RecruitmentProxy(resultSet.getLong(ID_RECRUITMENT_COL)));
		applicationForm.setInterviews(getInterviews(id));
		applicationForm.setPhotoScope(resultSet.getString(PHOTO_SCOPE_COL));
		applicationForm.setStatus(new Status(resultSet.getLong(ID_STATUS_COL), resultSet.getString("title")));
		applicationForm.setUser(new UserProxy(resultSet.getLong(ID_USER_COL)));
		applicationForm.setQuestions(getQuestions(id));
		return applicationForm;
	};

	private static final String SQL_GET_BY_ID = "SELECT a." + ID_COL + ", a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
			+ ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a.id = ?;";
	private static final String SQL_GET_BY_USER_ID = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
			+ ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
			+ ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a." + ID_USER_COL + " = ?;";
	private static final String SQL_GET_BY_STATUS = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
			+ IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + "\n" + "WHERE s.title = ?;";
	private static final String SQL_GET_BY_STATE = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
			+ IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + "\n" + "WHERE a.is_active = ?;";
	private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " a WHERE a." + ID_COL + " = ?;";
	private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ID_STATUS_COL + ", " + IS_ACTIVE_COL
			+ ", " + ID_RECRUITMENT_COL + ", " + PHOTO_SCOPE_COL + ", " + "" + ID_USER_COL + ", " + DATE_CREATE_COL
			+ ", " + FEEDBACK + ") VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_GET_ALL = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
			+ ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
			+ DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + " \n;";
	private static final String SQL_UPDATE = "UPDATE \"" + TABLE_NAME + "\" SET " + ID_STATUS_COL + " = ?, "
			+ IS_ACTIVE_COL + "  = ?, " + PHOTO_SCOPE_COL + " = ?, " + DATE_CREATE_COL + " = ?, " + FEEDBACK + " = ?"
			+ "WHERE " + ID_COL + " = ?";
	private static final String SQL_GET_INTERVIEWS = "SELECT i.id\n" + "FROM \"interview\" i\n"
			+ "WHERE i.id_application_form = ?";
	private static final String SQL_GET_CURRENT = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
			+ ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
			+ ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a.id_status \n"
			+ "WHERE a.id_user = ? AND a.id_recruitment = (SELECT r.id FROM recruitment r WHERE r.end_date > CURRENT_DATE)";

	private static final String SQL_GET_LAST = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
			+ ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
			+ ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a.id_status \n"
			+ "WHERE a.id_user = ? AND a.date_create = (SELECT MAX(a_in.date_create) from application_form a_in where a_in.id_user = ?)";

	private static final String SQL_GET_BY_INTERVIEWER = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
			+ ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
			+ ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
			+ "\" a INNER JOIN status s ON s.id = a.id_status \n"
			+ "WHERE EXISTS (SELECT i.id FROM interview i WHERE i.id_application_form = a." + ID_COL
			+ " AND i.id_interviewer = ?) AND a." + IS_ACTIVE_COL + " = true AND a." + ID_STATUS_COL + " = "
			+ StatusEnum.APPROVED.getId();

	private static final String SQL_GET_COUNT_APP_FORM_STATUS = "select count(id_status) AS \"approved_to_work\" from \""+TABLE_NAME+"\" where id_status=?";


	private static final String SQL_CHANGE_STATUS = "UPDATE application_form SET id_status = ? where id_status = ?;";

	private static final String SQL_IS_ASSIGNED = "SELECT EXISTS( SELECT i.id FROM interview i WHERE i.interviewer_role = ? AND i.id_application_form = ? )";

	private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());

	public ApplicationFormDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public ApplicationForm getById(Long id) {
		log.info("Looking for application form with id = ", id);
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
	}

	@Override
	public List<ApplicationForm> getByUserId(Long id) {
		log.info("Looking for application forms of user with id = {}", id);
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_USER_ID, extractor, id);
	}

	@Override
	public List<ApplicationForm> getByStatus(String status) {
		log.info("Looking for application forms with status = ", status);
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_STATUS, extractor, status);
	}

	@Override
	public List<ApplicationForm> getByState(boolean state) {
		log.info("Looking for application forms with is_active = ", state);
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_STATE, extractor, state);
	}

	@Override
	public int deleteApplicationForm(ApplicationForm applicationForm) {
		log.info("Deleting application form with id = ", applicationForm.getId());
		return this.getJdbcTemplate().update(SQL_DELETE, applicationForm.getId());
	}

	@Override
	public Long insertApplicationForm(ApplicationForm applicationForm, Connection connection) {
		log.info("Inserting application forms with user_id = " + applicationForm.getUser().getId());
		Recruitment recruitment = applicationForm.getRecruitment();
		return this.getJdbcTemplate().insert(SQL_INSERT, connection, applicationForm.getStatus().getId(),
				applicationForm.isActive(), recruitment != null ? recruitment.getId() : null,
				applicationForm.getPhotoScope(), applicationForm.getUser().getId(), applicationForm.getDateCreate(),
				applicationForm.getFeedback());
	}

	@Override
	public int updateApplicationForm(ApplicationForm applicationForm) {
		log.info("Updating application forms with id = " + applicationForm.getId());
		return this.getJdbcTemplate().update(SQL_UPDATE, applicationForm.getStatus().getId(),
				applicationForm.isActive(), applicationForm.getPhotoScope(), applicationForm.getDateCreate(),
				applicationForm.getFeedback(), applicationForm.getId());
	}

	@Override
	public List<ApplicationForm> getAll() {
		log.info("Get all application forms");
		return this.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
	}

	@Override
	public Long getCountRejectedAppForm() {
		log.info("Looking for Count Rejected AppForm");
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(8));
	}

	@Override
	public Long getCountToWorkAppForm() {
		log.info("Looking for Count To Work AppForm");
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(5));
	}

	@Override
	public Long getCountGeneralAppForm() {
		log.info("Looking for Count General AppForm");
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(6));
	}

	@Override
	public Long getCountAdvancedAppForm() {
		log.info("Looking for Count Advanced AppForm");
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(7));
	}

	;

	private List<Interview> getInterviews(Long applicationFormId) {
		return this.getJdbcTemplate().queryForList(SQL_GET_INTERVIEWS, (ResultSetExtractor<Interview>) resultSet -> {
			InterviewProxy interviewProxy = new InterviewProxy(resultSet.getLong("id"));
			return interviewProxy;
		}, applicationFormId);
	}

	private static final String SQL_GET_ANSWERS = "SELECT fa.id\n FROM \"form_answer\" fa\n WHERE fa.id_application_form = ?;";

	private List<FormAnswer> getAnswers(Long applicationFormId) {
		return this.getJdbcTemplate().queryForList(SQL_GET_ANSWERS, resultSet -> {
			FormAnswer formAnswerProxy = new FormAnswerProxy(resultSet.getLong("id"));
			return formAnswerProxy;
		}, applicationFormId);
	}

	private List<FormQuestion> getQuestions(Long applicationFormId) {
		return this.getJdbcTemplate().queryWithParameters("SELECT distinct q.id from form_question q join form_answer "
				+ "a on (q.id= a.id_question) where a.id_application_form = ?;", resultSet -> {
					List<FormQuestion> questions = new ArrayList<>();
					do {
						questions.add(new FormQuestionProxy(resultSet.getLong(FormQuestionDaoImpl.ID_COL)));
					} while (resultSet.next());
					return questions;
				}, applicationFormId);
	}

	@Override
	public ApplicationForm getCurrentApplicationFormByUserId(Long id) {
		log.trace("Looking for current application form with user id = {}", id);
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_CURRENT, extractor, id);
	}

	@Override
	public ApplicationForm getLastApplicationFormByUserId(Long id) {
		log.trace("Looking for last application form with user id = {}", id);
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_LAST, extractor, id, id);
	}

	@Override
	public List<ApplicationForm> getByInterviewer(User interviewer) {
		log.trace("Looking for application forms, thar are assigned for interviewer with id = {}", interviewer.getId());
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_INTERVIEWER, extractor, interviewer.getId());
	}

	@Override
	public boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role) {
		return this.getJdbcTemplate().queryWithParameters(SQL_IS_ASSIGNED, resultSet -> resultSet.getBoolean(1),
				role.getId(), applicationForm.getId());
	}

	@Override
	public int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus) {
		return this.getJdbcTemplate().update(SQL_CHANGE_STATUS,fromIdStatus,toIdStatus);
	}

}
