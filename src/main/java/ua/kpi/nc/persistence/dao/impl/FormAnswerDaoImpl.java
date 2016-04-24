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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Nikita on 22.04.2016.
 */
public class FormAnswerDaoImpl extends JdbcDaoSupport implements FormAnswerDao {
	private static final String SQL_GET_BY_ID = "SELECT fa.id, fa.answer, fa.id_question, fa.id_application_form, fa.id_variant, fa.id_interview \n"
			+ "FROM form_answer fa \n" + "WHERE fa.id = ?;";
	private static final String SQL_GET_BY_INTERVIEW_AND_QUESTION = "SELECT fa.id, fa.answer, fa.id_question, fa.id_application_form, fa.id_variant, fa.id_interview \n"
			+ "FROM form_answer fa \n" + "WHERE fa.id_interview= ? and  fa.id_question = ?;";
	private static final String SQL_INSERT = "INSERT INTO form_answer(answer, id_question, id_application_form, id_variant, id_interview) \n"
			+ "VALUES (?,?,?,?,?);";
	private static final String SQL_UPDATE = "UPDATE form_answer SET answer = ? WHERE id= ?;";
	private static final String SQL_DELETE = "DELETE FROM form_answer WHERE form_answer.id = ?;";
	private static Logger log = LoggerFactory.getLogger(FormAnswerDaoImpl.class.getName());

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
	public Set<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question) {
		if (log.isInfoEnabled()) {
			log.info("Looking for answer with interview_id= " + interview.getId() + "and question= "
					+ question.getTitle());
		}
		return this.getJdbcTemplate().queryForSet(SQL_GET_BY_INTERVIEW_AND_QUESTION,
				new FormAnswerDaoImpl.FormAnswerExtractor(), interview.getId(), question.getId());
	}

	@Override
	public Long insertFormAnswer(FormAnswer formAnswer, Interview interview, FormQuestion question,
			FormAnswerVariant answerVariant, ApplicationForm applicationForm) {
		if (log.isInfoEnabled()) {
			log.info("Inserting form answer with interview_id= " + interview.getId() + ", question_id= "
					+ question.getTitle() + ", application form id=" + applicationForm.getId()
					+ ", form answer variant id= " + answerVariant.getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, formAnswer.getAnswer(), question.getId(),
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

	private final class FormAnswerExtractor implements ResultSetExtractor<FormAnswer> {
		@Override
		public FormAnswer extractData(ResultSet resultSet) throws SQLException {
			FormAnswer formAnswer = new FormAnswerImpl();
			formAnswer.setId(resultSet.getLong("id"));
			formAnswer.setAnswer(resultSet.getString("answer"));
			formAnswer.setInterview(new InterviewProxy(resultSet.getLong("id_interview")));
			formAnswer.setApplicationForm(new ApplicationFormProxy(resultSet.getLong("id_application_form")));
			formAnswer.setFormAnswerVariant(new FormAnswerVariantProxy(resultSet.getLong("id_variant")));
			formAnswer.setFormQuestion(new FormQuestionProxy(resultSet.getLong("id_question")));
			return formAnswer;
		}
	}

	@Override
	public Long insertFormAnswerForApplicationForm(FormAnswer formAnswer, FormQuestion question,
			FormAnswerVariant answerVariant, ApplicationForm applicationForm) {
		if (log.isInfoEnabled()) {
			log.info("Inserting form answer with question_id= " + question.getTitle() + ", application form id="
					+ applicationForm.getId() + ", form answer variant id= " + answerVariant.getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, formAnswer.getAnswer(), question.getId(),
				applicationForm.getId(), answerVariant != null ? answerVariant.getId() : null, null);
	}

	@Override
	public Long insertFormAnswerForInterview(FormAnswer formAnswer, FormQuestion question,
			FormAnswerVariant answerVariant, Interview interview) {
		if (log.isInfoEnabled()) {
			log.info("Inserting form answer with interview_id= " + interview.getId() + ", question_id= "
					+ question.getTitle() + ", form answer variant id= " + answerVariant.getId());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, formAnswer.getAnswer(), question.getId(), null,
				answerVariant != null ? answerVariant.getId() : null, interview.getId());
	}
}