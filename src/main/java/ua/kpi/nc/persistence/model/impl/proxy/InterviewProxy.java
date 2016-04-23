package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.real.InterviewImpl;
import ua.kpi.nc.service.InterviewService;
import ua.kpi.nc.service.ServiceFactory;

import java.sql.Timestamp;
import java.util.Set;

public class InterviewProxy implements Interview {

	private static final long serialVersionUID = 1092260058342461849L;

	private Long id;

	private InterviewImpl interview;

	private InterviewService service;
	private Set<FormAnswer> answers;

	public InterviewProxy() {
	}

	public InterviewProxy(Long id) {
		super();
		this.id = id;
	}

	@Override
	public Set<FormAnswer> getAnswers() {
		return answers;
	}

	@Override
	public void setAnswers(Set<FormAnswer> answers) {
		this.answers = answers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMark() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getMark();
	}

	public void setMark(int mark) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setMark(mark);
	}

	public Timestamp getDate() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getDate();
	}

	public void setDate(Timestamp date) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setDate(date);
	}

	public User getUser() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getUser();
	}

	public void setUser(User user) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setUser(user);
	}

	public Role getRole() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getRole();
	}

	public void setRole(Role role) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setRole(role);
	}

	public boolean isAdequateMark() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.isAdequateMark();
	}

	public void setAdequateMark(boolean adequateMark) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setAdequateMark(adequateMark);
	}

	public ApplicationForm getApplicationForm() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getApplicationForm();
	}

	public void setApplicationForm(ApplicationForm applicationForm) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setApplicationForm(applicationForm);
	}

	public Set<FormAnswer> getFormAnswers() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getFormAnswers();
	}

	public void setFormAnswers(Set<FormAnswer> answers) {

		interview.setFormAnswers(answers);
	}

	private void checkInterview(){
		if (interview == null) {
			service = ServiceFactory.getInterviewService();
			interview = downloadInterview();
		}
	}

	private InterviewImpl downloadInterview() {
		return (InterviewImpl) service.getInterviewById(id);
	}

}
