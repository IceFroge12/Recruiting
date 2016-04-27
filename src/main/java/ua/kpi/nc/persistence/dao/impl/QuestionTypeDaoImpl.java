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

	static final String TABLE_NAME = "form_question_type";
	static final String ID_COL = "id";
	static final String TYPE_TITLE_COL = "type_title";

	private static final String SQL_GET_ID = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME
			+ " WHERE " + ID_COL + " = ?";

	private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + TYPE_TITLE_COL + ") VALUES(?);";

	private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + " = ?;";

	public QuestionTypeDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public QuestionType getById(Long id) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for form question with id  = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_ID, new FormQuestionTypeExtractor(), id);
	}

	@Override
	public Long persistQuestionType(QuestionType questionType) {
		return this.getJdbcTemplate().insert(SQL_INSERT, questionType.getTypeTitle());

	}

	@Override
	public int deleteQuestionType(QuestionType questionType) {
		return this.getJdbcTemplate().update(SQL_DELETE, questionType.getId());
	}

	private static final class FormQuestionTypeExtractor implements ResultSetExtractor<QuestionType> {

		@Override
		public QuestionType extractData(ResultSet resultSet) throws SQLException {
			QuestionType questionType = new QuestionType();
			questionType.setId(resultSet.getLong(ID_COL));
			questionType.setTypeTitle(resultSet.getString(TYPE_TITLE_COL));
			return questionType;
		}
	}

}
