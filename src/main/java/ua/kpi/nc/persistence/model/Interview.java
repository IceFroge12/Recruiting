package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

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

    public User getIdInterviewer();

    public void setIdInterviewer(User idInterviewer);

    public Role getInterviewerRole();

    public void setInterviewerRole(Role interviewerRole);

    public boolean isAdequateMark();

    public void setAdequateMark(boolean adequateMark);

    public ApplicationForm getApplicationFormId();

    public void setApplicationFormId(ApplicationForm applicationFormId);
}
