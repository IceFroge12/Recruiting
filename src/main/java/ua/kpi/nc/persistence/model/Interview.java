package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface Interview extends Serializable{
    public Long getId();
    public void setId(Long id);

    public int getMark();

    public void setMark(int mark);

    public Timestamp getDate();

    public void setDate(Timestamp date);

    public User getUser();

    public void setUser(User user);

    public Role getRole();

    public void setRole(Role role);

    public boolean isAdequateMark();

    public void setAdequateMark(boolean adequateMark);

    public ApplicationForm getApplicationForm();

    public void setApplicationForm(ApplicationForm applicationForm);
    
    public Set<FormAnswer> getFormAnswers();
    
    public void setFormAnswers(Set<FormAnswer> answers);
}
