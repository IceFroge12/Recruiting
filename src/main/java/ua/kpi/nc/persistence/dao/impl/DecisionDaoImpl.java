package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DecisionDao;
import ua.kpi.nc.persistence.model.Decision;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 22.04.2016.
 */
public class DecisionDaoImpl extends JdbcDaoSupport implements DecisionDao {

    private static Logger log = LoggerFactory.getLogger(DecisionDaoImpl.class.getName());

    public DecisionDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    static final String TABLE_NAME = "decision_matrix";

    static final String SOFT_MARK_COL = "soft_mark";
    static final String TECH_MARK_COL = "tech_mark";
    static final String FINAL_MARK_COL = "final_mark";
    static final String SCALE_COL = "scale";

    private static final String SQL_GET_ALL = "SELECT " + SOFT_MARK_COL + ", " + TECH_MARK_COL + ", " + FINAL_MARK_COL + ", " +
        SCALE_COL + " FROM decision_matrix";

    private static final String SQL_GET_BY_IDS = SQL_GET_ALL + " WHERE " + SOFT_MARK_COL + " = ? and " + TECH_MARK_COL
            + " = ?;";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + SOFT_MARK_COL + ", " + TECH_MARK_COL
            + ", " + FINAL_MARK_COL + ", " + SCALE_COL + ") VALUES (?,?,?,?)";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + FINAL_MARK_COL + " = ? WHERE "
            + SOFT_MARK_COL + " = ? AND " + TECH_MARK_COL + " = ? AND "+ SCALE_COL + " = ? ";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + SOFT_MARK_COL + " = ? AND "
            + TECH_MARK_COL + " = ? AND " + FINAL_MARK_COL + " = ?;";

    private static final String SQL_TRUNCATE = "TRUNCATE TABLE "+TABLE_NAME;

    @Override
    public Decision getByMarks(int softMark, int techMark) {
        if (log.isInfoEnabled()) {
            log.info("Looking for decision with soft_mark = " + softMark + " and tech_mark = " + techMark);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_IDS, new DecisionExtractor(), softMark, techMark);
    }

    @Override
    public Long insertDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Insert decision with soft_mark = " + decision.getSoftMark() + " tech_mark = "
                    + decision.getTechMark() + " and final_mark = " + decision.getFinalMark());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, decision.getSoftMark(), decision.getTechMark(),
                decision.getFinalMark());
    }

    @Override
    public int updateDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Update decision with soft_mark = " + decision.getSoftMark() + " and tech_mark = "
                    + decision.getTechMark());
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, decision.getFinalMark(), decision.getSoftMark(),
                decision.getTechMark());
    }

    @Override
    public int deleteDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Delete decision with soft_mark = " + decision.getSoftMark() + " tech_mark = "
                    + decision.getTechMark() + " and final_mark = " + decision.getFinalMark());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, decision.getSoftMark(), decision.getTechMark(),
                decision.getFinalMark());
    }

    @Override
    public int truncateDecisionTable() {
        if (log.isInfoEnabled()) {
            log.info("Clear table decisions = ");
        }
        return this.getJdbcTemplate().update(SQL_TRUNCATE);
    }


    private final class DecisionExtractor implements ResultSetExtractor<Decision> {
        @Override
        public Decision extractData(ResultSet resultSet) throws SQLException {
            Decision decision = new Decision();
            decision.setSoftMark(resultSet.getInt(SOFT_MARK_COL));
            decision.setTechMark(resultSet.getInt(TECH_MARK_COL));
            decision.setFinalMark(resultSet.getInt(FINAL_MARK_COL));
            decision.setFinalMark(resultSet.getInt(SCALE_COL));
            return decision;
        }
    }

    @Override
    public List<Decision> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Getting all decisions");
        }
        return this.getJdbcTemplate().queryForList(SQL_GET_ALL, new DecisionExtractor());
    }


}
