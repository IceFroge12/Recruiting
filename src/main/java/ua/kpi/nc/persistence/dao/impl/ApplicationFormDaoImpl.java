package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.proxy.FormAnswerProxy;
import ua.kpi.nc.persistence.model.impl.proxy.InterviewProxy;
import ua.kpi.nc.persistence.model.impl.proxy.RecruitmentProxy;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ApplicationFormDaoImpl extends JdbcDaoSupport implements ApplicationFormDao {

    private static final String SQL_GET_BY_ID = "SELECT a.id, a.id_status, a.is_active,a.id_recruitment, a.photo_scope, "
            + "a.id_user, a.date_create, s.title \n"
            + "FROM \"application_form\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a.id = ?;";
    private static final String SQL_GET_BY_USER_ID = "SELECT a.id, a.id_status, a.is_active,a.id_recruitment, a.photo_scope, "
            + "a.id_user, a.date_create, s.title \n"
            + "FROM \"application_form\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a.id_user = ?;";
    private static final String SQL_GET_BY_STATUS = "SELECT a.id, a.id_status, a.is_active,a.id_recruitment, a.photo_scope, "
            + "a.id_user, a.date_create, s.title \n"
            + "FROM \"application_form\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE s.title = ?;";
    private static final String SQL_DELETE = "DELETE FROM \"application_form a\" WHERE a.id = ?;";
    private static final String SQL_INSERT = "INSERT INTO application_form (id_status, is_active, id_recruitment, photo_scope, "
            + "id_user, date_create) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ALL = "SELECT a.id, a.id_status, a.is_active,a.id_recruitment, a.photo_scope, "
            + "a.id_user, a.date_create, s.title \n"
            + "FROM \"application_form\" a INNER JOIN status s ON s.id = a.id_status \n;";
    private static final String SQL_UPDATE = "UPDATE \"application_form\" SET id_status = ?, is_active  = ?,"
            + " id_recruitment = ?, photo_scope = ?, date_create = ? " + "WHERE id = ?";
    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());

    public ApplicationFormDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public ApplicationForm getById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Looking for application form with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ApplicationFormExtractor(), id);
    }

    @Override
    public Set<ApplicationForm> getByUserId(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Looking for application forms of user with id = " + id);
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_BY_USER_ID, new ApplicationFormExtractor(), id);
    }

    @Override
    public Set<ApplicationForm> getByStatus(String status) {
        if (log.isInfoEnabled()) {
            log.info("Looking for application forms with status = " + status);
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_BY_STATUS, new ApplicationFormExtractor(), status);
    }

    @Override
    public Set<ApplicationForm> getByState(boolean state) {
        if (log.isInfoEnabled()) {
            log.info("Looking for application forms with is_active = " + state);
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_BY_STATUS, new ApplicationFormExtractor(), state);
    }

    @Override
    public int deleteApplicationForm(ApplicationForm applicationForm) {
        if (log.isInfoEnabled()) {
            log.info("Deleting application form with id = " + applicationForm.getId());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, applicationForm.getId());
    }

    @Override
    public Long insertApplicationForm(ApplicationForm applicationForm, User user, Connection connection) {
        if (log.isInfoEnabled()) {
            log.info("Inserting application forms with user_id = " + user.getId());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, connection, applicationForm.getStatus().getId(),
                applicationForm.isActive(), applicationForm.getRecruitment().getId(), applicationForm.getPhotoScope(),
                applicationForm.getUser().getId(), applicationForm.getDateCreate());
    }

    @Override
    public int updateApplicationForm(ApplicationForm applicationForm) {
        if (log.isInfoEnabled()) {
            log.info("Updating application forms with id = " + applicationForm.getId());
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, applicationForm.getStatus().getId(),
                applicationForm.isActive(), applicationForm.getRecruitment().getId(), applicationForm.getPhotoScope(),
                applicationForm.getDateCreate(), applicationForm.getId());
    }

    @Override
    public Set<ApplicationForm> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Get all application forms");
        }
        return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, new ApplicationFormExtractor());
    }

    private Set<Interview> getInterviews(Long applicationFormId) {
        return this.getJdbcTemplate().queryWithParameters(
                "SELECT i.id\n" + "FROM \"interview\" i\n" + "WHERE i.id_application_form = ?;", resultSet -> {
                    Set<Interview> set = new HashSet<>();
                    do {
                        set.add(new InterviewProxy(resultSet.getLong("id")));
                    } while (resultSet.next());
                    return set;
                }, applicationFormId);
    }

    private Set<FormAnswer> getAnswers(Long applicationFormId) {
        return this.getJdbcTemplate().queryWithParameters(
                "SELECT fa.id\n" + "FROM \"form_answer\" fa\n" + "WHERE fa.id_application_form = ?;", resultSet -> {
                    Set<FormAnswer> set = new HashSet<>();
                    do {
                        set.add(new FormAnswerProxy(resultSet.getLong("id")));
                    } while (resultSet.next());
                    return set;
                }, applicationFormId);
    }

    private final class ApplicationFormExtractor implements ResultSetExtractor<ApplicationForm> {

        @Override
        public ApplicationForm extractData(ResultSet resultSet) throws SQLException {
            ApplicationForm applicationForm = new ApplicationFormImpl();
            long applicationFormId = resultSet.getLong("id_user");
            applicationForm.setActive(resultSet.getBoolean("is_active"));
            applicationForm.setAnswers(getAnswers(applicationFormId));
            applicationForm.setDateCreate(resultSet.getTimestamp("date_create"));
            applicationForm.setId(applicationFormId);
            applicationForm.setRecruitment(new RecruitmentProxy(resultSet.getLong("id_recruitment")));
            applicationForm.setInterviews(getInterviews(applicationFormId));
            applicationForm.setPhotoScope(resultSet.getString("photo_scope"));
            applicationForm.setStatus(new Status(resultSet.getLong("id_status"), resultSet.getString("title")));
            applicationForm.setUser(new UserProxy(resultSet.getLong("id_user")));
            return applicationForm;
        }

    }

}
