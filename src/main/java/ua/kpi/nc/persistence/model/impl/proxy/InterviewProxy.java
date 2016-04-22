package ua.kpi.nc.persistence.model.impl.proxy;

import java.sql.Timestamp;
import java.util.Set;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.InterviewImpl;
import ua.kpi.nc.service.InterviewService;

public class InterviewProxy implements Interview {

	private static final long serialVersionUID = 1092260058342461849L;

	private Long id;

	private InterviewImpl interview;

	private InterviewService service;

	public InterviewProxy() {
	}

	public InterviewProxy(Long id) {
		super();
		this.id = id;
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

	public User getIdInterviewer() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getIdInterviewer();
	}

	public void setIdInterviewer(User idInterviewer) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setIdInterviewer(idInterviewer);
	}

	public Role getInterviewerRole() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getInterviewerRole();
	}

	public void setInterviewerRole(Role interviewerRole) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setInterviewerRole(interviewerRole);
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

	public ApplicationForm getApplicationFormId() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getApplicationFormId();
	}

	public void setApplicationFormId(ApplicationForm applicationFormId) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setApplicationFormId(applicationFormId);
	}

	public Set<FormAnswer> getFormAnswers() {
		if (interview == null) {
			interview = downloadInterview();
		}
		return interview.getFormAnswers();
	}

	public void setFormAnswers(Set<FormAnswer> answers) {
		if (interview == null) {
			interview = downloadInterview();
		}
		interview.setFormAnswers(answers);
	}

	private InterviewImpl downloadInterview() {
		return (InterviewImpl) service.getInterviewById(id);
	}

}
