package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.proxy.FormAnswerProxy;
import ua.kpi.nc.persistence.model.impl.proxy.FormAnswerVariantProxy;
import ua.kpi.nc.persistence.model.impl.proxy.RoleProxy;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikita on 23.04.2016.
 */
public class FormQuestionDaoImpl extends JdbcDaoSupport implements FormQuestionDao {

    private ResultSetExtractor<FormQuestion> extractor = resultSet -> {
        FormQuestion formQuestion = new FormQuestionImpl();
        formQuestion.setId(resultSet.getLong(ID_COL));
        formQuestion.setEnable(resultSet.getBoolean(ENABLE_COL));
        formQuestion.setMandatory(resultSet.getBoolean(MANDATORY_COL));
        formQuestion.setOrder(resultSet.getInt(ORDER_COL));
        formQuestion.setRoles(getRoles(resultSet.getLong(ID_COL)));
        formQuestion.setTitle(resultSet.getString(TITLE_COL));
        formQuestion.setQuestionType(new QuestionType(resultSet.getLong(ID_QUESTION_TYPE_COL),
                resultSet.getString(QuestionTypeDaoImpl.TYPE_TITLE_COL)));
        formQuestion.setFormAnswerVariants(getAnswerVariants(resultSet.getLong(ID_COL)));
        return formQuestion;
    };

    static final String ROLE_MAP_TABLE_NAME = "form_question_role";
    static final String ROLE_MAP_TABLE_ROLE_ID = "id_role";
    static final String ROLE_MAP_TABLE_FORM_QUESTION_ID = "id_form_question";

    static final String TABLE_NAME = "form_question";

    static final String ID_COL = "id";
    static final String TITLE_COL = "title";
    static final String ID_QUESTION_TYPE_COL = "id_question_type";
    static final String ENABLE_COL = "enable";
    static final String MANDATORY_COL = "mandatory";
    static final String ORDER_COL = "order";

    private static final String SQL_GET_ALL = "SELECT fq." + ID_COL + ", fq." + TITLE_COL + ", fq."
            + ID_QUESTION_TYPE_COL + ", fq." + ENABLE_COL + ", fq." + MANDATORY_COL + ", fq." + ORDER_COL + ", fqt."
            + QuestionTypeDaoImpl.TYPE_TITLE_COL + " FROM " + TABLE_NAME + " fq INNER JOIN "
            + QuestionTypeDaoImpl.TABLE_NAME + " fqt ON fqt." + QuestionTypeDaoImpl.ID_COL + " = fq."
            + ID_QUESTION_TYPE_COL + "";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE fq." + ID_COL + " = ?;";

    private static final String SQL_GET_BY_ROLE = SQL_GET_ALL + " INNER JOIN " + ROLE_MAP_TABLE_NAME + " fqr ON fqr."
            + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = fq." + ID_COL + " WHERE fqr." + ROLE_MAP_TABLE_ROLE_ID + " = ?;";
    private static final String SQL_GET_ENABLE_BY_ROLE = SQL_GET_ALL + " INNER JOIN " + ROLE_MAP_TABLE_NAME + " fqr ON fqr."
            + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = fq." + ID_COL + " WHERE fqr." + ROLE_MAP_TABLE_ROLE_ID + " = ? AND fq." + ENABLE_COL + " = true";
    
    private static final String SQL_GET_BY_APPLICATION_FORM = SQL_GET_ALL + " INNER JOIN form_answer fa ON fa.id_question = fq.id WHERE fa.id_application_form = ?;";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " ( " + TITLE_COL + ", " + ENABLE_COL + ", "
            + MANDATORY_COL + ", " + ID_QUESTION_TYPE_COL + ") " + "VALUES (?,?,?,?);";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + TITLE_COL + " = ?, " + ENABLE_COL
            + " = ?, " + MANDATORY_COL + " = ?, \"" + ORDER_COL + "\" = ? WHERE " + ID_COL + " = ?;";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?";

    private static Logger log = LoggerFactory.getLogger(FormQuestionDaoImpl.class.getName());

    public FormQuestionDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Long insertFormQuestion(FormQuestion formQuestion, Connection connection) {
        log.info("Insert question with title, enable, mandatory =", formQuestion.getTitle(),
                formQuestion.isEnable(), formQuestion.isMandatory());
        return this.getJdbcTemplate().insert(SQL_INSERT, formQuestion.getTitle(), formQuestion.isEnable(),
                formQuestion.isMandatory(), formQuestion.getQuestionType().getId());
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        log.info("Deleting form question with id = ", formQuestion.getId());
        return this.getJdbcTemplate().update(SQL_DELETE, formQuestion.getId());
    }

    private static final String SQL_ROLE_MAP_INSERT = "INSERT INTO " + ROLE_MAP_TABLE_NAME + " ("
            + ROLE_MAP_TABLE_FORM_QUESTION_ID + ", " + ROLE_MAP_TABLE_ROLE_ID + ") VALUES (?,?)";

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role) {
        if (formQuestion.getId() == null) {
            log.warn("User don`t have id", formQuestion.getTitle());
            return false;
        }
        return this.getJdbcTemplate().insert(SQL_ROLE_MAP_INSERT, formQuestion.getId(), role.getId()) > 0;
    }

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role, Connection connection) {
        if (formQuestion.getId() == null) {
            log.warn("User: don`t have id", formQuestion.getTitle());
            return false;
        }
        return this.getJdbcTemplate().insert(SQL_ROLE_MAP_INSERT, connection, formQuestion.getId(), role.getId()) > 0;
    }

    @Override
    public int deleteRole(FormQuestion formQuestion, Role role) {
        if (formQuestion.getId() == null) {
            log.warn("Form question: don`t have id", formQuestion.getTitle());
            return 0;
        }
        return this.getJdbcTemplate().update("DELETE FROM " + ROLE_MAP_TABLE_NAME + " WHERE "
                        + ROLE_MAP_TABLE_FORM_QUESTION_ID + "= ? AND " + ROLE_MAP_TABLE_ROLE_ID + "=?;", formQuestion.getId(),
                role.getId());
    }

    @Override
    public FormQuestion getById(Long id) {
        log.info("Looking for form question with id = {}", id);
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<FormQuestion> getByRole(Role role) {
        log.info("Looking for form question by role = {}", role.getRoleName());
        return this.getJdbcTemplate().queryForList(SQL_GET_BY_ROLE, extractor, role.getId());
    }

    @Override
    public List<FormQuestion> getAll() {
        log.info("Get all form questions");
        return this.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    private List<Role> getRoles(Long formQuestionID) {
        return this.getJdbcTemplate().queryWithParameters("SELECT fqr." + ROLE_MAP_TABLE_ROLE_ID + " FROM "
                        + ROLE_MAP_TABLE_NAME + " fqr\n" + "WHERE fqr." + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = ?;",
                resultSet -> {
                    List<Role> roles = new ArrayList<>();
                    do {
                        roles.add(new RoleProxy(resultSet.getLong(ROLE_MAP_TABLE_ROLE_ID)));
                    } while (resultSet.next());
                    return roles;
                }, formQuestionID);
    }

    private List<FormAnswerVariant> getAnswerVariants(Long formQuestionID) {
        return this.getJdbcTemplate().queryWithParameters("SELECT fav.id FROM form_answer_variant fav WHERE fav.id_question = ? ORDER BY fav.id",
                resultSet -> {
                    List<FormAnswerVariant> answersVariants = new ArrayList<>();
                    do {
                        answersVariants.add(new FormAnswerVariantProxy(resultSet.getLong(FormAnswerVariantDaoImpl.ID_COL)));
                    } while (resultSet.next());
                    return answersVariants;
                }, formQuestionID);
    }

    @Override
    public int updateFormQuestion(FormQuestion formQuestion) {
        if ((formQuestion.getId() == null) && (log.isDebugEnabled())) {
            log.warn("Form question: don`t have id", formQuestion.getTitle());
            return 0;
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, formQuestion.getTitle(), formQuestion.isEnable(),
                formQuestion.isMandatory(),formQuestion.getOrder(), formQuestion.getId());
    }

	@Override
	public Set<FormQuestion> getEnableByRoleAsSet(Role role) {
		log.info("Looking for set of form questions by role = {}", role.getRoleName());
        return this.getJdbcTemplate().queryForSet(SQL_GET_ENABLE_BY_ROLE, extractor, role.getId());
	}

	@Override
	public List<FormQuestion> getEnableByRole(Role role) {
		log.info("Looking for form question by role = {}", role.getRoleName());
		return this.getJdbcTemplate().queryForList(SQL_GET_ENABLE_BY_ROLE, extractor, role.getId());
	}

	@Override
	public Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm) {
		return this.getJdbcTemplate().queryForSet(SQL_GET_BY_APPLICATION_FORM, extractor, applicationForm.getId());
	}

}
