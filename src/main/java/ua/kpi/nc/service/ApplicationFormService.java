package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ApplicationForm;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface ApplicationFormService {

	ApplicationForm getApplicationFormById(Long id);
	
	
}
