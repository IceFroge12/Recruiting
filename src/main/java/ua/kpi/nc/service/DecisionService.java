package ua.kpi.nc.service;

import java.util.List;

import ua.kpi.nc.persistence.model.Decision;
import ua.kpi.nc.persistence.model.Status;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface DecisionService {
	
	Decision getByMarks(int softMark, int techMark);

    Long insertDecision(Decision decision);

    int updateDecision(Decision decision);

    int deleteDecision(Decision decision);
    
    List<Decision> getAll();
	
    Status getStatusByFinalMark(int finalMark);
    
    void updateDecisionMatrix(List<Decision> decisionMatrix);
}
