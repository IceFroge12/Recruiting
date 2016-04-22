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

/**
 * Created by Алексей on 22.04.2016.
 */
public class DecisionDaoImpl extends JdbcDaoSupport implements DecisionDao {

    private static Logger log = LoggerFactory.getLogger(DecisionDaoImpl.class.getName());

    public DecisionDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private static final String SQL_GET_BY_IDS = "SELECT soft_mark, tech_mark, final_mark FROM \"decision_matrix\" WHERE soft_mark = ? and tech_mark=?;";

    private static final String SQL_INSERT = "INSERT INTO \"decision_matrix\"(soft_mark, tech_mark, final_mark VALUES (?,?,?);";

    private static final String SQL_UPDATE = "UPDATE \"decision_matrix\" SET final_mark = ? WHERE soft_mark = ? and tech_mark = ?";

    private static final String SQL_DELETE = "DELETE FROM \"decision_matrix\" WHERE soft_mark = ? and tech_mark = ? and final_mark = ?;";

    @Override
    public Decision getByMarks(int softMark, int techMark) {
        if (log.isInfoEnabled()){
            log.info("Looking for decision with soft_mark = " + softMark+" and tech_mark = " + techMark);
        }
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_IDS, new DecisionExtractor(), softMark, techMark);
    }

    @Override
    public Long insertDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Insert decision with soft_mark = " + decision.getSoftMark()+" tech_mark = " + decision.getTechMark()+
                    " and final_mark = " + decision.getFinalMark());
        }
        return this.getJdbcTemplate().insert(SQL_INSERT, decision.getSoftMark(),decision.getTechMark(),decision.getFinalMark());
    }

    @Override
    public int updateDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Update decision with soft_mark = " + decision.getSoftMark()+" and tech_mark = " + decision.getTechMark());
        }
        return this.getJdbcTemplate().update(SQL_UPDATE, decision.getFinalMark(),decision.getSoftMark(),decision.getTechMark());
    }

    @Override
    public int deleteDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Delete decision with soft_mark = " + decision.getSoftMark()+" tech_mark = " + decision.getTechMark()+
                    " and final_mark = " + decision.getFinalMark());
        }
        return this.getJdbcTemplate().update(SQL_DELETE, decision.getSoftMark(), decision.getTechMark(), decision.getFinalMark());
    }

    private final class DecisionExtractor implements ResultSetExtractor<Decision> {
        @Override
        public Decision extractData(ResultSet resultSet) throws SQLException {
            Decision decision = new Decision();
            decision.setSoftMark(resultSet.getInt("soft_mark"));
            decision.setTechMark(resultSet.getInt("tech_mark"));
            decision.setFinalMark(resultSet.getInt("final_mark"));
            return decision;
        }
    }

}
