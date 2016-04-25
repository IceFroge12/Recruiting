package ua.kpi.nc.persistence.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface ApplicationForm {

	public Long getId();

	public void setId(Long id);

	public Status getStatus();

	public void setStatus(Status status);

	public boolean isActive();

	public void setActive(boolean active);

	public Recruitment getRecruitment();

	public void setRecruitment(Recruitment recruitment);

	public String getPhotoScope();

	public void setPhotoScope(String photoScope);

	public User getUser();

	public void setUser(User user);

	public Timestamp getDateCreate();

	public void setDateCreate(Timestamp dateCreate);

	public Set<Interview> getInterviews();

	public void setInterviews(Set<Interview> interviews);

	public Set<FormAnswer> getAnswers();

	public void setAnswers(Set<FormAnswer> answers);
}
