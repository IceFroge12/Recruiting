package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.proxy.ApplicationFormProxy;
import ua.kpi.nc.persistence.model.impl.proxy.FormAnswerVariantProxy;
import ua.kpi.nc.persistence.model.impl.proxy.FormQuestionProxy;
import ua.kpi.nc.persistence.model.impl.proxy.InterviewProxy;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikita on 22.04.2016.
 */
public class FormAnswerDaoImpl extends JdbcDaoSupport implements FormAnswerDao {

	static final String TABLE_NAME = "form_answer";

	static final String ID_COL = "id";
	static final String ANSWER_COL = "answer";
	static final String ID_QUESTION_COL = "id_question";
	static final String ID_VARIANT_COL = "id_variant";
	static final String ID_INTERVIEW_COL = "id_interview";
	static final String ID_APPLICATION_FORM_COL = "id_application_form";

	private static final String SQL_GET = "SELECT fa." + ID_COL + ", fa." + ANSWER_COL + ", fa." + ID_QUESTION_COL
			+ ", fa." + ID_APPLICATION_FORM_COL + ", fa." + ID_VARIANT_COL + ", fa." + ID_INTERVIEW_COL + " FROM "
			+ TABLE_NAME + " fa";

	private static final String SQL_GET_BY_ID = SQL_GET + " WHERE fa." + ID_COL + " = ?;";
	private static final String SQL_GET_BY_INTERVIEW_AND_QUESTION = SQL_GET + " WHERE fa." + ID_INTERVIEW_COL
			+ "= ? and  fa." + ID_QUESTION_COL + " = ?;";
	private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ANSWER_COL + ", " + ID_QUESTION_COL
			+ ", " + ID_APPLICATION_FORM_COL + ", " + ID_VARIANT_COL + ", " + ID_INTERVIEW_COL + ") \n"
			+ "VALUES (?,?,?,?,?);";
	private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + ANSWER_COL + " = ? WHERE " + ID_COL
			+ "= ?;";
	private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?;";
	private static Logger log = LoggerFactory.getLogger(FormAnswerDaoImpl.class.getName());

	private final class FormAnswerExtractor implements ResultSetExtractor<FormAnswer> {
		@Override
		public FormAnswer extractData(ResultSet resultSet) throws SQLException {
			FormAnswer formAnswer = new FormAnswerImpl();
			formAnswer.setId(resultSet.getLong(ID_COL));
			formAnswer.setAnswer(resultSet.getString(ANSWER_COL));
			formAnswer.setInterview(new InterviewProxy(resultSet.getLong(ID_INTERVIEW_COL)));
			formAnswer.setApplicationForm(new ApplicationFormProxy(resultSet.getLong(ID_APPLICATION_FORM_COL)));
			formAnswer.setFormAnswerVariant(new FormAnswerVariantProxy(resultSet.getLong(ID_VARIANT_COL)));
			formAnswer.setFormQuestion(new FormQuestionProxy(resultSet.getLong(ID_QUESTION_COL)));
			return formAnswer;
		}
	}

	public FormAnswerDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public FormAnswer getById(Long id) {
		if (log.isInfoEnabled()) {
			log.info("Looking for answer with id = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new FormAnswerDaoImpl.FormAnswerExtractor(),
				id);
	}

	@Override
	public List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question) {
		if (log.isInfoEnabled()) {
			log.info("Looking for answer with interview_id= " + interview.getId() + "and question= "
					+ question.getTitle());
		}
		return this.getJdbcTemplate().queryForList(SQL_GET_BY_INTERVIEW_AND_QUESTION,
				new FormAnswerDaoImpl.FormAnswerExtractor(), interview.getId(), question.getId());
	}

	@Override
	public Long insertFormAnswer(FormAnswer formAnswer, Interview interview, FormQuestion question,
			FormAnswerVariant answerVariant, ApplicationForm applicationForm, Connection connection) {
		if (log.isInfoEnabled()) {
			log.info("Inserting form answer with interview_id= " + interview.getId() + ", question_id= "
					+ question.getTitle() + ", application form id=" + applicationForm.getId()
					+ ", form answer variant id= " + answerVariant.getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, connection, formAnswer.getAnswer(), question.getId(),
				applicationForm.getId(), answerVariant != null ? answerVariant.getId() : null, interview.getId());
	}

	@Override
	public int updateFormAnswer(FormAnswer formAnswer) {

		if (log.isInfoEnabled()) {
			log.info("Update form answer with id = " + formAnswer.getId());
		}
		return this.getJdbcTemplate().update(SQL_UPDATE, formAnswer.getAnswer(), formAnswer.getId());
	}

	@Override
	public int deleteFormAnswer(FormAnswer formAnswer) {
		if (log.isInfoEnabled()) {
			log.info("Delete form answer with id = " + formAnswer.getId());
		}
		return this.getJdbcTemplate().update(SQL_DELETE, formAnswer.getId());
	}

	@Override
	public Long insertFormAnswerForApplicationForm(FormAnswer formAnswer, FormQuestion question,
			FormAnswerVariant answerVariant, ApplicationForm applicationForm, Connection connection) {
		if (log.isInfoEnabled()) {
			log.info("Inserting form answer with question_id= " + question.getTitle() + ", application form id="
					+ applicationForm.getId() + ", form answer variant id= " + answerVariant.getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, connection, formAnswer.getAnswer(), question.getId(),
				applicationForm.getId(), answerVariant != null ? answerVariant.getId() : null, null);
	}

	@Override
	public Long insertFormAnswerForInterview(FormAnswer formAnswer, FormQuestion question,
			FormAnswerVariant answerVariant, Interview interview, Connection connection) {
		if (log.isInfoEnabled()) {
			log.info("Inserting form answer with interview_id= " + interview.getId() + ", question_id= "
					+ question.getTitle() + ", form answer variant id= " + answerVariant.getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, connection, formAnswer.getAnswer(), question.getId(), null,
				answerVariant != null ? answerVariant.getId() : null, interview.getId());
	}
}