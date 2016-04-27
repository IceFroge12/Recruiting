package ua.kpi.nc.service;

import java.io.IOException;
import java.util.Set;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface ApplicationFormService {

	ApplicationForm getApplicationFormById(Long id);

	Set<ApplicationForm> getByUserId(Long id);

	Set<ApplicationForm> getByStatus(String status);

	Set<ApplicationForm> getByState(boolean state);

	int deleteApplicationForm(ApplicationForm applicationForm);

	boolean insertApplicationForm(ApplicationForm applicationForm, User user, Set<FormAnswer> formAnswers) throws IOException;

	int updateApplicationForm(ApplicationForm applicationForm);

	Set<ApplicationForm> getAll();
}
