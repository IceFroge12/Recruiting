package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.DecisionDao;
import ua.kpi.nc.persistence.model.Decision;
import ua.kpi.nc.service.DecisionService;

public class DecisionServiceImpl implements DecisionService {

	private DecisionDao decisionDao;

	public DecisionServiceImpl(DecisionDao decisionDao) {
		this.decisionDao = decisionDao;
	}

	@Override
	public Decision getByMarks(int softMark, int techMark) {
		return decisionDao.getByMarks(softMark, techMark);
	}

	@Override
	public Long insertDecision(Decision decision) {
		return decisionDao.insertDecision(decision);
	}

	@Override
	public int updateDecision(Decision decision) {
		return decisionDao.updateDecision(decision);
	}

	@Override
	public int deleteDecision(Decision decision) {
		return decisionDao.deleteDecision(decision);
	}

}
