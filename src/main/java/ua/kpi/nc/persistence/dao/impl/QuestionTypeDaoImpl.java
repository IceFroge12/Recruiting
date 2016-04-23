package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.QuestionTypeDao;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by IO on 21.04.2016.
 */
public class QuestionTypeDaoImpl extends JdbcDaoSupport implements QuestionTypeDao {

    private static Logger log = LoggerFactory.getLogger(QuestionTypeDaoImpl.class.getName());

    public QuestionTypeDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public QuestionType getById(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form question with id  = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT form_question_type.id, form_question_type.type_title FROM public.form_question_type WHERE form_question_type.id = ?;",
                new FormQuestionTypeExtractor(), id);
    }

    @Override
    public Long persistQuestionType(QuestionType questionType) {
        return this.getJdbcTemplate().insert("INSERT INTO public.form_question_type(type_title) VALUES(?);", questionType.getTypeTitle());

    }

    @Override
    public int deleteQuestionType(QuestionType questionType) {
        return this.getJdbcTemplate().update("DELETE FROM public.form_question_type WHERE form_question_type.id = ?;", questionType.getId());
    }


    private static final class FormQuestionTypeExtractor implements ResultSetExtractor<QuestionType> {

        @Override
        public QuestionType extractData(ResultSet resultSet) throws SQLException {
            QuestionType questionType = new QuestionType();
            questionType.setId(resultSet.getLong("id"));
            questionType.setTypeTitle(resultSet.getString("type_title"));
            return questionType;
        }
    }


}
