package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface ApplicationForm extends Serializable {

	Long getId();

	void setId(Long id);

	Status getStatus();

	void setStatus(Status status);

	boolean isActive();

	void setActive(boolean active);

	Recruitment getRecruitment();

	void setRecruitment(Recruitment recruitment);

	String getPhotoScope();

	void setPhotoScope(String photoScope);

	User getUser();

	void setUser(User user);

	Timestamp getDateCreate();

	void setDateCreate(Timestamp dateCreate);

	Set<Interview> getInterviews();

	void setInterviews(Set<Interview> interviews);

	Set<FormAnswer> getAnswers();

	void setAnswers(Set<FormAnswer> answers);
}
