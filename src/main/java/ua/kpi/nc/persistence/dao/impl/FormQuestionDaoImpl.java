package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.impl.proxy.FormAnswerProxy;
import ua.kpi.nc.persistence.model.impl.proxy.RoleProxy;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nikita on 23.04.2016.
 */
public class FormQuestionDaoImpl extends JdbcDaoSupport implements FormQuestionDao {

    private static final String SQL_GET_BY_ID =
            "SELECT fq.id, fq.title, fq.id_question_type, fq.enable, fq.mandatory, fqt.type_title \n" +
                    "FROM form_question fq INNER JOIN form_question_type fqt ON fqt.id = fq.id_question_type \n" +
                    "WHERE fq.id = ?;";
    private static final String SQL_GET_ALL =
            "SELECT fq.id, fq.title, fq.id_question_type, fq.enable, fq.mandatory, fqt.type_title \n" +
                    "FROM form_question fq INNER JOIN form_question_type fqt ON fqt.id = fq.id_question_type ;";
    private static final String SQL_GET_BY_ROLE =
            "SELECT fq.id, fq.title, fq.id_question_type, fq.enable, fq.mandatory \n" +
                    "FROM form_question fq INNER JOIN form_question_role fqr ON fqr.id_form_question = fq.id \n" +
                    "WHERE fqr.id_role = ?;";
    private static final String SQL_INSERT = "INSERT INTO form_question( title, enable, mandatory, id_question_type) " +
            "VALUES (?,?,?,?);";
    private static final String SQL_DELETE = "DELETE FROM form_question WHERE form_question.id = ?;";
    private static Logger log = LoggerFactory.getLogger(FormQuestionDaoImpl.class.getName());

    public FormQuestionDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Long insertFormQuestion(FormQuestion formQuestion, QuestionType questionType, Connection connection) {
        if (log.isInfoEnabled()) {
            log.info("Insert question with title=" + formQuestion.getTitle() + ", enable=" + formQuestion.isEnable() +
                    ", mandatory= " + formQuestion.isMandatory());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, formQuestion.getTitle(), formQuestion.isEnable(),
                formQuestion.isMandatory(), questionType.getId());
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        if (log.isInfoEnabled()) {
            log.info("Deleting form question with id = " + formQuestion.getId());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, formQuestion.getId());
    }

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role) {
        if ((formQuestion.getId() == null) && (log.isDebugEnabled())) {
            log.warn("User: " + formQuestion.getTitle() + " don`t have id");
            return false;
        }
        return this.getJdbcTemplate().insert("INSERT INTO form_question_role(id_form_question, id_role) VALUES (?,?);",
                formQuestion.getId(), role.getId()) > 0;
    }


    @Override
    public boolean addRole(FormQuestion formQuestion, Role role, Connection connection) {
        if ((formQuestion.getId() == null) && (log.isDebugEnabled())) {
            log.warn("User: " + formQuestion.getTitle() + " don`t have id");
            return false;
        }
        return this.getJdbcTemplate().insert("INSERT INTO form_question_role(id_form_question, id_role) VALUES (?,?);",
                connection, formQuestion.getId(), role.getId()) > 0;
    }


    @Override
    public int deleteRole(FormQuestion formQuestion, Role role) {
        if ((formQuestion.getId() == null) && (log.isDebugEnabled())) {
            log.warn("Form question: " + formQuestion.getTitle() + " don`t have id");
            return 0;
        }
        return this.getJdbcTemplate().update("DELETE FROM form_question_role WHERE id_form_question= ? AND id_role=?;",
                formQuestion.getId(), role.getId());
    }

    @Override
    public FormQuestion getById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Looking for form question with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new FormQuestionExtractor(), id);
    }

    @Override
    public Set<FormQuestion> getByRole(Role role) {
        if (log.isInfoEnabled()) {
            log.info("Looking for form question by role = " + role.getRoleName());
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_BY_ROLE, new FormQuestionExtractor(), role.getId());
    }

    @Override
    public Set<FormQuestion> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Get all form questions");
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, new FormQuestionExtractor());
    }

    private Set<Role> getRoles(Long formQuestionID) {
        return this.getJdbcTemplate().queryWithParameters("SELECT fqr.id_role\n" +
                "FROM form_question_role fqr\n" +
                "WHERE fqr.id_form_question = ?;", resultSet -> {
            Set<Role> roles = new HashSet<>();
            do {
                roles.add(new RoleProxy(resultSet.getLong("id_role")));
            } while (resultSet.next());
            return roles;
        }, formQuestionID);
    }

    private Set<FormAnswer> getAnswers(Long formQuestionID) {
        return this.getJdbcTemplate().queryWithParameters("SELECT fa.id\n" +
                "FROM form_answer fa\n" +
                "WHERE fa.id_question = ?;", resultSet -> {
            Set<FormAnswer> answers = new HashSet<>();
            do {
                answers.add(new FormAnswerProxy(resultSet.getLong("id")));
            } while (resultSet.next());
            return answers;
        }, formQuestionID);
    }

    private final class FormQuestionExtractor implements ResultSetExtractor<FormQuestion> {
        @Override
        public FormQuestion extractData(ResultSet resultSet) throws SQLException {
            FormQuestion formQuestion = new FormQuestionImpl();
            formQuestion.setId(resultSet.getLong("id"));
            formQuestion.setEnable(resultSet.getBoolean("enable"));
            formQuestion.setMandatory(resultSet.getBoolean("mandatory"));
            formQuestion.setRoles(getRoles(resultSet.getLong("id")));
            formQuestion.setTitle(resultSet.getString("title"));
            formQuestion.setAnswers(getAnswers(resultSet.getLong("id")));
            formQuestion.setQuestionType(new QuestionType(resultSet.getLong("id_question_type"), resultSet.getString("type_title")));
            return formQuestion;
        }
    }
}
