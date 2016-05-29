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
public class ResendMessageDaoImpl implements ResendMessageDao {

    private final JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<ResendMessage> extractor = resultSet -> {
        ResendMessage resendMessage = new ResendMessage();
        resendMessage.setId(resultSet.getLong(ID_COL));
        resendMessage.setSubject(resultSet.getString(SUBJECT_COL));
        resendMessage.setText(resultSet.getString(TEXT_COL));
        resendMessage.setEmail(resultSet.getString(EMAIL_COL));
        return resendMessage;
    };

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
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public ResendMessage getById(Long id) {
        log.info("Getting Resend Messages with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public ResendMessage getBySubject(String subject) {
        log.info("Getting Resend Messages with subject = {}", subject);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_SUBJECT, extractor, subject);
    }

    @Override
    public Long insert(ResendMessage resendMessage) {
        log.info("Inserting Resend Messages with id = {}", resendMessage.getId());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, resendMessage.getId(), resendMessage.getSubject(),
                resendMessage.getText(), resendMessage.getEmail());
    }

    @Override
    public int delete(ResendMessage resendMessage) {
        log.info("Deleting Resend Messages with Id = {}", resendMessage.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, resendMessage.getId());
    }

    @Override
    public List<ResendMessage> getAll() {
        log.info("Getting all Resend Messages");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }
}
