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

	private ResultSetExtractor<Decision> extractor = resultSet -> {
        Decision decision = new Decision();
        decision.setSoftMark(resultSet.getInt(SOFT_MARK_COL));
        decision.setTechMark(resultSet.getInt(TECH_MARK_COL));
        decision.setFinalMark(resultSet.getInt(FINAL_MARK_COL));
        return decision;
    };

	private static final String SQL_GET_ALL = "SELECT " + SOFT_MARK_COL + ", " + TECH_MARK_COL + ", " + FINAL_MARK_COL
			+ " FROM decision_matrix";

	private static final String SQL_GET_BY_IDS = SQL_GET_ALL + " WHERE " + SOFT_MARK_COL + " = ? and " + TECH_MARK_COL
			+ " = ?;";

	private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + SOFT_MARK_COL + ", " + TECH_MARK_COL
			+ ", " + FINAL_MARK_COL + ") VALUES (?,?,?)";

	private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + FINAL_MARK_COL + " = ? WHERE "
			+ SOFT_MARK_COL + " = ? AND " + TECH_MARK_COL + " = ?";

	private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + SOFT_MARK_COL + " = ? AND "
			+ TECH_MARK_COL + " = ? AND " + FINAL_MARK_COL + " = ?;";

	@Override
	public Decision getByMarks(int softMark, int techMark) {
		log.info("Looking for decision with soft_mark, tech_mark = ",softMark, techMark);
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_IDS, extractor, softMark, techMark);
	}

	@Override
	public Long insertDecision(Decision decision) {
		log.info("Insert decision with soft_mark, tech_mark, final_mark = ", decision.getSoftMark(),
				decision.getTechMark(), decision.getFinalMark());
		return this.getJdbcTemplate().insert(SQL_INSERT, decision.getSoftMark(), decision.getTechMark(),
				decision.getFinalMark());
	}

	@Override
	public int updateDecision(Decision decision) {
		log.info("Update decision with soft_mark, tech_mark  = ", decision.getSoftMark(), decision.getTechMark());
		return this.getJdbcTemplate().update(SQL_UPDATE, decision.getFinalMark(), decision.getSoftMark(),
				decision.getTechMark());
	}

	@Override
	public int deleteDecision(Decision decision) {
		log.info("Delete decision with soft_mark, tech_mark,final_mark = ",decision.getSoftMark(),
				decision.getTechMark(), decision.getFinalMark());
		return this.getJdbcTemplate().update(SQL_DELETE, decision.getSoftMark(), decision.getTechMark(),
				decision.getFinalMark());
	}
	
	@Override
	public List<Decision> getAll() {
		log.info("Getting all decisions");
		return this.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
	}

}
