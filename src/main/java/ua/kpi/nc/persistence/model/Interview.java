package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface Interview extends Serializable {
    public Long getId();

    public void setId(Long id);

    public Role getRole();

    public void setRole(Role role);

    public List<FormAnswer> getAnswers();

    public void setAnswers(List<FormAnswer> answers);

    public Integer getMark();

    public void setMark(Integer mark);

    public Timestamp getDate();

    public void setDate(Timestamp date);

    public User getInterviewer();

    public void setInterviewer(User user);

    public Boolean isAdequateMark();

    public void setAdequateMark(Boolean adequateMark);

    public ApplicationForm getApplicationForm();

    public void setApplicationForm(ApplicationForm applicationForm);

}
