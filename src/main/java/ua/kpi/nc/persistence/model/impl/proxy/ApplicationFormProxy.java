package ua.kpi.nc.persistence.model.impl.proxy;

import java.sql.Timestamp;
import java.util.Set;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.service.ApplicationFormService;

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
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getStatus();
	}

	@Override
	public void setStatus(Status status) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setStatus(status);
	}

	@Override
	public boolean isActive() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.isActive();
	}

	@Override
	public void setActive(boolean active) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setActive(active);
	}

	@Override
	public Recruitment getRecruitment() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getRecruitment();
	}

	@Override
	public void setRecruitment(Recruitment recruitment) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setRecruitment(recruitment);
	}

	@Override
	public String getPhotoScope() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getPhotoScope();
	}

	@Override
	public void setPhotoScope(String photoScope) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setPhotoScope(photoScope);
	}

	@Override
	public User getUser() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getUser();
	}

	@Override
	public void setUser(User user) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setUser(user);
	}

	@Override
	public Timestamp getDateCreate() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getDateCreate();
	}

	@Override
	public void setDateCreate(Timestamp dateCreate) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setDateCreate(dateCreate);
	}

	@Override
	public Set<Interview> getInterviews() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getInterviews();
	}

	@Override
	public void setInterviews(Set<Interview> interviews) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setInterviews(interviews);
	}

	@Override
	public Set<FormAnswer> getAnswers() {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		return applicationFormImpl.getAnswers();
	}

	@Override
	public void setAnswers(Set<FormAnswer> answers) {
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
		applicationFormImpl.setAnswers(answers);
	}

	private ApplicationFormImpl downloadApplicationForm(Long id) {
		return applicationFormImpl = (ApplicationFormImpl) service.getApplicationFormById(id);
	}

}
