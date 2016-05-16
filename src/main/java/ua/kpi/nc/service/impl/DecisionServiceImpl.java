package ua.kpi.nc.service.impl;

import java.util.List;

import ua.kpi.nc.persistence.dao.DecisionDao;
import ua.kpi.nc.persistence.model.Decision;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.service.DecisionService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.StatusService;

public class DecisionServiceImpl implements DecisionService {

	private DecisionDao decisionDao;
	private StatusService statusService;
	
	public DecisionServiceImpl(DecisionDao decisionDao) {
		this.decisionDao = decisionDao;
		statusService = ServiceFactory.getStatusService();
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

	@Override
	public List<Decision> getAll() {
		return decisionDao.getAll();
	}

	@Override
	public int truncateDecisionTable() {
		return decisionDao.truncateDecisionTable();
	}

	@Override
	public Status getStatusByFinalMark(int finalMark) {
		switch(finalMark) {
		case 3:
			return statusService.getStatusById(StatusEnum.APPROVED_TO_JOB.getId());
		case 2:
			return statusService.getStatusById(StatusEnum.APPROVED_TO_GENERAL_COURSES.getId());
		case 1:
			return statusService.getStatusById(StatusEnum.REJECTED.getId());
		}
		return null;
	}

}
