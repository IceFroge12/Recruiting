package ua.kpi.nc.persistence.dao;

import java.util.List;

import ua.kpi.nc.persistence.model.Decision;

/**
 * Created by Алексей on 22.04.2016.
 */
public interface DecisionDao {
	Decision getByMarks(int softMark, int techMark);

	Long insertDecision(Decision decision);

	int updateDecision(Decision decision);

	int deleteDecision(Decision decision);

	List<Decision> getAll();
}
