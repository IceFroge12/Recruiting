package ua.kpi.nc.persistence.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.impl.proxy.FormQuestionProxy;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerVariantImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

/**
 * Created by Алексей on 22.04.2016.
 */
public class FormAnswerVariantDaoImpl extends JdbcDaoSupport implements FormAnswerVariantDao {

    private static Logger log = LoggerFactory.getLogger(FormAnswerVariantDaoImpl.class.getName());

    public FormAnswerVariantDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private ResultSetExtractor<FormAnswerVariant> extractor = resultSet -> {
        FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl();
        formAnswerVariant.setId(resultSet.getLong(ID_COL));
        formAnswerVariant.setAnswer(resultSet.getString("" + ANSWER_COL + ""));
        formAnswerVariant.setFormQuestion(new FormQuestionProxy(resultSet.getLong(ID_QUESTION_COL)));
        return formAnswerVariant;
    };

    static final String TABLE_NAME = "form_answer_variant";

    static final String ID_COL = "id";
    static final String ANSWER_COL = "answer";
    static final String ID_QUESTION_COL = "id_question";

    private static final String SQL_GET = "SELECT " + ID_COL + ", " + ANSWER_COL + ", " + ID_QUESTION_COL + " from \""
            + TABLE_NAME + "\"";

    private static final String SQL_GET_BY_ID = SQL_GET + " WHERE " + ID_COL + " = ?;";
    
    private static final String SQL_GET_BY_TITLE_QUESTION = SQL_GET + " WHERE " + ANSWER_COL + " = ? AND " + ID_QUESTION_COL + " = ?";

    private static final String SQL_GET_BY_QUESTION_ID = SQL_GET + " WHERE " + ID_QUESTION_COL + " = ? ORDER BY " + ID_COL;

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ANSWER_COL + ", " + ID_QUESTION_COL
            + ") VALUES (?,?);";

    private static final String SQL_UPDATE = "UPDATE \"" + TABLE_NAME + "\" " + "SET " + ANSWER_COL + " = ?, "
            + ID_QUESTION_COL + " = ? WHERE " + ID_COL + " = ?;";

    private static final String SQL_DELETE = "DELETE FROM \"" + TABLE_NAME + "\" WHERE \"" + TABLE_NAME + "\".id = ?;";

    @Override
    public FormAnswerVariant getById(Long id) {
        log.info("Looking for FormAnswerVarian with id = {}", id);
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<FormAnswerVariant> getByQuestionId(Long id) {
        log.info("Looking for FormAnswerVarian with QuestionId = {}", id);
        return this.getJdbcTemplate().queryForList(SQL_GET_BY_QUESTION_ID, extractor, id);
    }

    @Override
    public Long insertFormAnswerVariant(FormAnswerVariant formatVariant, Connection connection) {
        log.info("Insert FormAnswerVariant with answer = {}", formatVariant.getAnswer());
        return this.getJdbcTemplate().insert(SQL_INSERT, connection, formatVariant.getAnswer(),
                formatVariant.getFormQuestion().getId());
    }

    @Override
    public Long insertFormAnswerVariant(FormAnswerVariant formatVariant) {
        log.info("Insert FormAnswerVariant with answer = {}", formatVariant.getAnswer());
        return this.getJdbcTemplate().insert(SQL_INSERT, formatVariant.getAnswer(),
                formatVariant.getFormQuestion().getId());
    }

    @Override
    public int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        log.info("Update FormAnswerVariant with answer = {}", formAnswerVariant.getAnswer());
        return this.getJdbcTemplate().update(SQL_UPDATE, formAnswerVariant.getAnswer(),
                formAnswerVariant.getFormQuestion().getId(), formAnswerVariant.getId());
    }

    @Override
    public int deleteFormAnswerVariant(FormAnswerVariant formVariant) {
        log.info("Delete formVariant with id = {}", formVariant.getId());
        return this.getJdbcTemplate().update(SQL_DELETE, formVariant.getId());
    }

    @Override
    public List<FormAnswerVariant> getAll() {
        log.info("Get all FormAnswerVariant");
        return this.getJdbcTemplate().queryForList(SQL_GET, extractor);
    }

	@Override
	public FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question) {
		log.trace("Looking for answer variant with title = {} and questionId = {})", title, question.getId());
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE_QUESTION, extractor, title, question.getId());
	}
}
