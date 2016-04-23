package ua.kpi.nc.persistence.model.impl.real;

import java.sql.Timestamp;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Алексей on 21.04.2016.
 */
public class ApplicationFormImpl implements ApplicationForm {

	private Long id;

	private Status status;

	private boolean active;

	private Recruitment recruitment;

	private String photoScope;

	private User user;

	private Timestamp dateCreate;

	private Set<Interview> interviews;

	private Set<FormAnswer> answers;

	public ApplicationFormImpl() {
	}

	public ApplicationFormImpl(Status status, boolean active, Recruitment recruitment, String photoScope, User user, Timestamp dateCreate, Set<Interview> interviews, Set<FormAnswer> answers) {
		this.status = status;
		this.active = active;
		this.recruitment = recruitment;
		this.photoScope = photoScope;
		this.user = user;
		this.dateCreate = dateCreate;
		this.interviews = interviews;
		this.answers = answers;
	}

	public ApplicationFormImpl(Long id, Status status, boolean active, Recruitment recruitment, String photoScope,
							   User user, Timestamp dateCreate, Set<Interview> interviews, Set<FormAnswer> answers) {
		super();
		this.id = id;
		this.status = status;
		this.active = active;
		this.recruitment = recruitment;
		this.photoScope = photoScope;
		this.user = user;
		this.dateCreate = dateCreate;
		this.interviews = interviews;
		this.answers = answers;
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
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public Recruitment getRecruitment() {
		return recruitment;
	}

	@Override
	public void setRecruitment(Recruitment recruitment) {
		this.recruitment = recruitment;
	}

	@Override
	public String getPhotoScope() {
		return photoScope;
	}

	@Override
	public void setPhotoScope(String photoScope) {
		this.photoScope = photoScope;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Timestamp getDateCreate() {
		return dateCreate;
	}

	@Override
	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Override
	public Set<Interview> getInterviews() {
		return interviews;
	}

	@Override
	public void setInterviews(Set<Interview> interviews) {
		this.interviews = interviews;
	}

	@Override
	public Set<FormAnswer> getAnswers() {
		return answers;
	}

	@Override
	public void setAnswers(Set<FormAnswer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "ApplicationFormImpl [id=" + id + ", status=" + status + ", active=" + active + ", recruitment="
				+ recruitment + ", photoScope=" + photoScope + ", user=" + user + ", dateCreate=" + dateCreate + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(active).append(dateCreate).append(id).append(photoScope)
				.append(status).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationFormImpl other = (ApplicationFormImpl) obj;
		return new EqualsBuilder().append(active, other.active).append(dateCreate, other.dateCreate)
				.append(id, other.id).append(interviews, other.interviews).append(photoScope, other.photoScope)
				.append(status, other.status).isEquals();
	}

}
