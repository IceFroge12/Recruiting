package ua.kpi.nc.persistence.model.impl.proxy;

import java.sql.Timestamp;
import java.util.List;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;

public class ApplicationFormProxy implements ApplicationForm {

	private Long id;

	private ApplicationFormImpl applicationFormImpl;

	private ApplicationFormService service;

	public ApplicationFormProxy() {
	}

	public ApplicationFormProxy(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Status getStatus() {
		checkApplicationForm();
		return applicationFormImpl.getStatus();
	}

	@Override
	public void setStatus(Status status) {
		checkApplicationForm();
		applicationFormImpl.setStatus(status);
	}

	@Override
	public boolean isActive() {
		checkApplicationForm();
		return applicationFormImpl.isActive();
	}

	@Override
	public void setActive(boolean active) {
		checkApplicationForm();
		applicationFormImpl.setActive(active);
	}

	@Override
	public Recruitment getRecruitment() {
		checkApplicationForm();
		return applicationFormImpl.getRecruitment();
	}

	@Override
	public void setRecruitment(Recruitment recruitment) {
		checkApplicationForm();
		applicationFormImpl.setRecruitment(recruitment);
	}

	@Override
	public String getPhotoScope() {
		checkApplicationForm();
		return applicationFormImpl.getPhotoScope();
	}

	@Override
	public void setPhotoScope(String photoScope) {
		checkApplicationForm();
		applicationFormImpl.setPhotoScope(photoScope);
	}

	@Override
	public User getUser() {
		checkApplicationForm();
		return applicationFormImpl.getUser();
	}

	@Override
	public void setUser(User user) {
		checkApplicationForm();
		applicationFormImpl.setUser(user);
	}

	@Override
	public Timestamp getDateCreate() {
		checkApplicationForm();
		return applicationFormImpl.getDateCreate();
	}

	@Override
	public void setDateCreate(Timestamp dateCreate) {
		checkApplicationForm();
		applicationFormImpl.setDateCreate(dateCreate);
	}

	@Override
	public List<Interview> getInterviews() {
		checkApplicationForm();
		return applicationFormImpl.getInterviews();
	}

	@Override
	public void setInterviews(List<Interview> interviews) {
		checkApplicationForm();
		applicationFormImpl.setInterviews(interviews);
	}

	@Override
	public List<FormAnswer> getAnswers() {
		checkApplicationForm();
		return applicationFormImpl.getAnswers();
	}

	@Override
	public void setAnswers(List<FormAnswer> answers) {
		checkApplicationForm();
		applicationFormImpl.setAnswers(answers);
	}

	private void checkApplicationForm(){
		if (applicationFormImpl == null) {
			service = ServiceFactory.getApplicationFormService();
			applicationFormImpl = downloadApplicationForm(id);
		}
	}

	private ApplicationFormImpl downloadApplicationForm(Long id) {
		return applicationFormImpl = (ApplicationFormImpl) service.getApplicationFormById(id);
	}

}
