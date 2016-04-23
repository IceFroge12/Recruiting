package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface Interview extends Serializable{
    Long getId();

    void setId(Long id);

    int getMark();

    void setMark(int mark);

    Timestamp getDate();

    void setDate(Timestamp date);

    User getUser();

    void setUser(User user);

    Role getRole();

    void setRole(Role role);

    boolean isAdequateMark();

    void setAdequateMark(boolean adequateMark);

    ApplicationForm getApplicationForm();

    void setApplicationForm(ApplicationForm applicationForm);
    
    Set<FormAnswer> getFormAnswers();
    
    void setFormAnswers(Set<FormAnswer> answers);
}
