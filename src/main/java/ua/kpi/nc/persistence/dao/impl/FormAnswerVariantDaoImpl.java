package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.impl.proxy.FormQuestionProxy;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerVariantImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Алексей on 22.04.2016.
 */
public class FormAnswerVariantDaoImpl extends JdbcDaoSupport implements FormAnswerVariantDao {

    private static Logger log = LoggerFactory.getLogger(FormAnswerVariantDaoImpl.class.getName());

    public FormAnswerVariantDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private static final String SQL_GET_BY_ID = "SELECT id, answer, id_question from \"form_answer_variant\" where id=?;";

    private static final String SQL_GET_BY_QUESTION_ID = "SELECT id, answer, id_question from \"form_answer_variant\" where id_question=?;";

    private static final String SQL_INSERT = "INSERT INTO \"form_answer_variant\"(answer, id_question) VALUES (?,?);";

    private static final String SQL_UPDATE = "UPDATE \"form_answer_variant\" " +
            "SET answer = ?, id_question = ? WHERE id = ?;";

    private static final String SQL_DELETE = "DELETE FROM \"form_answer_variant\" WHERE \"form_answer_variant\".id = ?;";

    private static final String SQL_GET_ALL = "SELECT id, answer, id_question from \"form_answer_variant\"";

    @Override
    public FormAnswerVariant getById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Looking for FormAnswerVarian with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new FormAnswerVariantExtractor(), id);
    }

    @Override
    public Set<FormAnswerVariant> getByQuestionId(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Looking for FormAnswerVarian with QuestionId = " + id);
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_BY_QUESTION_ID, new FormAnswerVariantExtractor(), id);
    }

    @Override
    public Long insertFormAnswerVariant(FormAnswerVariant formatVariant, FormQuestion formQuestion,
                                        Connection connection) {
        if (log.isInfoEnabled()) {
            log.info("Insert FormAnswerVariant with answer = " + formatVariant.getAnswer());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, connection, formatVariant.getAnswer(),
                formQuestion.getId());
    }//Уточнить с транзакциями

    @Override
    public int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        if (log.isInfoEnabled()){
            log.info("Update FormAnswerVariant with answer = " + formAnswerVariant.getAnswer());
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, formAnswerVariant.getAnswer(),
                formAnswerVariant.getFormQuestion().getId(), formAnswerVariant.getId());
    }

    @Override
    public int deleteFormAnswerVariant(FormAnswerVariant formVariant) {
        if (log.isInfoEnabled()) {
            log.info("Delete formVariant with id = " + formVariant.getId());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, formVariant.getId());
    }

    @Override
    public Set<FormAnswerVariant> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Get all FormAnswerVariant");
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, new FormAnswerVariantExtractor());
    }

    private final class FormAnswerVariantExtractor implements ResultSetExtractor<FormAnswerVariant> {
        @Override
        public FormAnswerVariant extractData(ResultSet resultSet) throws SQLException {
            FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl();
            formAnswerVariant.setId(resultSet.getLong("id"));
            formAnswerVariant.setAnswer(resultSet.getString("answer"));
            formAnswerVariant.setFormQuestion(new FormQuestionProxy(resultSet.getLong("id_question")));
            return formAnswerVariant;
        }
    }
}
