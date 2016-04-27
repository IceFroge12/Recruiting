package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ResendMessageDao;
import ua.kpi.nc.persistence.model.ResendMessage;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Korzh
 */
public class ResendMessageDaoImpl extends JdbcDaoSupport implements ResendMessageDao {

    static final String TABLE_NAME = "resend_message";
    static final String ID_COL = "id";
    static final String SUBJECT_COL = "subject";
    static final String TEXT_COL = "text";
    static final String EMAIL_COL = "email";

    private static final String SQL_GET_ALL = "SELECT " + ID_COL + ", " + SUBJECT_COL + ", " + TEXT_COL + ", "
            + EMAIL_COL + ", " + SUBJECT_COL + " FROM " + TABLE_NAME;
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE " + TABLE_NAME + "." + ID_COL + "=?;";
    private static final String SQL_GET_BY_SUBJECT = SQL_GET_ALL + " WHERE " + TABLE_NAME + "." + SUBJECT_COL + "=?;";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ID_COL + ", " + SUBJECT_COL + ", "
            + TEXT_COL + ", " + EMAIL_COL + ") VALUES (?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + "  WHERE " + TABLE_NAME + "." + ID_COL + " = ?;";


    private static Logger log = LoggerFactory.getLogger(RecruitmentDaoImpl.class.getName());


    public ResendMessageDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }


    @Override
    public ResendMessage getById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Getting Resend Messages with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ResendMessageExtractor(), id);
    }

    @Override
    public ResendMessage getBySubject(String subject) {
        if (log.isInfoEnabled()) {
            log.info("Getting Resend Messages with subject = " + subject);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_SUBJECT, new ResendMessageExtractor(), subject);
    }

    @Override
    public Long insert(ResendMessage resendMessage) {
        if (log.isInfoEnabled()) {
            log.info("Inserting Resend Messages with id = " + resendMessage.getId());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, resendMessage.getId(), resendMessage.getSubject(),
                resendMessage.getText(), resendMessage.getEmail());
    }

    @Override
    public int delete(ResendMessage resendMessage) {
        if (log.isInfoEnabled()) {
            log.info("Deleting Resend Messages with Id = " + resendMessage.getId());
        }
        return this.getJdbcTemplate().update(SQL_DELETE,resendMessage.getId());
    }

    @Override
    public List<ResendMessage> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Getting all Resend Messages");
        }
        return this.getJdbcTemplate().queryForList(SQL_GET_ALL, new ResendMessageExtractor());
    }

    private final class ResendMessageExtractor implements ResultSetExtractor<ResendMessage> {
        @Override
        public ResendMessage extractData(ResultSet resultSet) throws SQLException {
            ResendMessage resendMessage = new ResendMessage();
            resendMessage.setId(resultSet.getLong(ID_COL));
            resendMessage.setSubject(resultSet.getString(SUBJECT_COL));
            resendMessage.setText(resultSet.getString(TEXT_COL));
            resendMessage.setEmail(resultSet.getString(EMAIL_COL));
            return resendMessage;
        }
    }
}
