package ua.kpi.nc.persistence.dao.impl;

import org.apache.log4j.Logger;
import ua.kpi.nc.persistence.dao.FormQuestionTypeDao;
import ua.kpi.nc.persistence.model.FormQuestionType;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;


/**
 * Created by IO on 21.04.2016.
 */
public class FormQuestionTypeDaoImpl extends JdbcDaoSupport implements FormQuestionTypeDao {

    private static Logger log = Logger.getLogger(FormQuestionTypeDaoImpl.class.getName());

    public FormQuestionTypeDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public FormQuestionType getById(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form question with id  = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT form_question_type.id, form_question_type.type_title FROM public.form_question_type WHERE form_question_type.id = ?;",
                new FormQuestionTypeExtractor(), id);
    }

    @Override
    public Long persistFormQuestionType(FormQuestionType formQuestionType) {
        return this.getJdbcTemplate().insert("INSERT INTO public.form_question_type(type_title) VALUES(?);", formQuestionType.getTypeTitle());

    }

    @Override
    public int deleteFormQuestionType(FormQuestionType formQuestionType) {
        return this.getJdbcTemplate().update("DELETE FROM public.form_question_type WHERE form_question_type.id = ?;", formQuestionType.getId());
    }


    private static final class FormQuestionTypeExtractor implements ResultSetExtractor<FormQuestionType> {

        @Override
        public FormQuestionType extractData(ResultSet resultSet) throws SQLException {
            FormQuestionType formQuestionType = new FormQuestionType();
            formQuestionType.setId(resultSet.getLong("id"));
            formQuestionType.setTypeTitle(resultSet.getString("type_title"));
            return formQuestionType;
        }
    }


}
