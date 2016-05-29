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
import java.util.List;

/**
 * Created by IO on 21.04.2016.
 */
public class QuestionTypeDaoImpl implements QuestionTypeDao {

    private final JdbcDaoSupport jdbcDaoSupport;

    private static Logger log = LoggerFactory.getLogger(QuestionTypeDaoImpl.class.getName());

    private ResultSetExtractor<QuestionType> extractor = resultSet -> {
        QuestionType questionType = new QuestionType();
        questionType.setId(resultSet.getLong(ID_COL));
        questionType.setTypeTitle(resultSet.getString(TYPE_TITLE_COL));
        return questionType;
    };

    static final String TABLE_NAME = "form_question_type";
    static final String ID_COL = "id";
    static final String TYPE_TITLE_COL = "type_title";

    private static final String SQL_GET_ID = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME
            + " WHERE " + ID_COL + " = ?";

    private static final String SQL_GET_BY_NAME = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME
            + " WHERE " + TYPE_TITLE_COL + " = ?";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + TYPE_TITLE_COL + ") VALUES(?);";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + " = ?;";

    private static final String SQL_GET_ALL = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME;

    public QuestionTypeDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    public List<QuestionType> getAllQuestionType() {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    @Override
    public QuestionType getById(Long id) {
        log.info("Looking for form question with id  = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_ID, extractor, id);
    }

    @Override
    public QuestionType getByName(String name) {
        log.info("Looking for form question with name  = {}", name);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_NAME, extractor, name);
    }

    @Override
    public Long persistQuestionType(QuestionType questionType) {
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, questionType.getTypeTitle());
    }

    @Override
    public int deleteQuestionType(QuestionType questionType) {
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, questionType.getId());
    }
}
