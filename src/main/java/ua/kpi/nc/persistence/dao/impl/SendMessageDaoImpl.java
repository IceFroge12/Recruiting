package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.SendMessageDao;
import ua.kpi.nc.persistence.model.Message;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Korzh
 */
public class SendMessageDaoImpl extends JdbcDaoSupport implements SendMessageDao {

    static final String TABLE_NAME = "resend_message";
    static final String ID_COL = "id";
    static final String SUBJECT_COL = "subject";
    static final String TEXT_COL = "text";
    static final String EMAIL_COL = "email";
    static final String STATUS_COL = "status";

    private static final String SQL_GET_ALL = "SELECT " + ID_COL + ", " + SUBJECT_COL + ", " + TEXT_COL + ", "
            + EMAIL_COL + ", " + STATUS_COL + " FROM " + TABLE_NAME + " WHERE " + STATUS_COL + "=false";
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE " + TABLE_NAME + "." + ID_COL + "=?;";
    private static final String SQL_GET_BY_SUBJECT = SQL_GET_ALL + " WHERE " + TABLE_NAME + "." + SUBJECT_COL + "=?;";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + SUBJECT_COL + ", "
            + TEXT_COL + ", " + EMAIL_COL + ", " + STATUS_COL + ") VALUES (?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + "  WHERE " + TABLE_NAME + "." + ID_COL + " = ?;";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + STATUS_COL + "=? WHERE " + ID_COL + "=?;";

    private static final String SQL_EXIST = "select exists(SELECT " + ID_COL + " FROM TABLE_NAME  where " + ID_COL + "=?)";


    private static Logger log = LoggerFactory.getLogger(RecruitmentDaoImpl.class.getName());


    public SendMessageDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }


    @Override
    public Message getById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("Getting Resend Messages with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new ResendMessageExtractor(), id);
    }

    @Override
    public Message getBySubject(String subject) {
        if (log.isInfoEnabled()) {
            log.info("Getting Resend Messages with subject = " + subject);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_SUBJECT, new ResendMessageExtractor(), subject);
    }

    @Override
    public Long insert(Message message) {
        if (log.isInfoEnabled()) {
            log.info("Inserting Resend Messages with id = " + message.getId());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, message.getSubject(),
                message.getText(), message.getEmail(), message.getStatus());
    }

    @Override
    public int delete(Message message) {
        if (log.isInfoEnabled()) {
            log.info("Deleting Resend Messages with Id = " + message.getId());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, message.getId());
    }

    @Override
    public List<Message> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Getting all Resend Messages");
        }
        return this.getJdbcTemplate().queryForList(SQL_GET_ALL, new ResendMessageExtractor());
    }

    @Override
    public int update(Message message) {
        if (log.isInfoEnabled()) {
            log.info("Update Resend Messages" + message.getId());
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, message.getStatus(), message.getId());
    }

    @Override
    public boolean isExist(Message message) {
        int cnt = this.getJdbcTemplate().update(SQL_EXIST, message.getId());
        return cnt > 0;
    }

    private final class ResendMessageExtractor implements ResultSetExtractor<Message> {
        @Override
        public Message extractData(ResultSet resultSet) throws SQLException {
            Message message = new Message();
            message.setId(resultSet.getLong(ID_COL));
            message.setSubject(resultSet.getString(SUBJECT_COL));
            message.setText(resultSet.getString(TEXT_COL));
            message.setEmail(resultSet.getString(EMAIL_COL));
            message.setStatus(resultSet.getBoolean(STATUS_COL));
            return message;
        }
    }
}
