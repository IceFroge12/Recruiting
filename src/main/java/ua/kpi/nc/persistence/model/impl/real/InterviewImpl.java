package ua.kpi.nc.persistence.model.impl.real;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

import java.sql.Timestamp;

/**
 * Created by Алексей on 21.04.2016.
 */
public class InterviewImpl implements Interview{

    private Long id;
    private int mark;
    private Timestamp date;
    private User idInterviewer;
    private Role interviewerRole;
    private boolean adequateMark;
    private ApplicationForm applicationFormId;

    public InterviewImpl() {
    }

    public InterviewImpl(Long id, int mark, Timestamp date, User idInterviewer, Role interviewerRole, boolean adequateMark, ApplicationForm applicationFormId) {
        this.id = id;
        this.mark = mark;
        this.date = date;
        this.idInterviewer = idInterviewer;
        this.interviewerRole = interviewerRole;
        this.adequateMark = adequateMark;
        this.applicationFormId = applicationFormId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getIdInterviewer() {
        return idInterviewer;
    }

    public void setIdInterviewer(User idInterviewer) {
        this.idInterviewer = idInterviewer;
    }

    public Role getInterviewerRole() {
        return interviewerRole;
    }

    public void setInterviewerRole(Role interviewerRole) {
        this.interviewerRole = interviewerRole;
    }

    public boolean isAdequateMark() {
        return adequateMark;
    }

    public void setAdequateMark(boolean adequateMark) {
        this.adequateMark = adequateMark;
    }

    public ApplicationForm getApplicationFormId() {
        return applicationFormId;
    }

    public void setApplicationFormId(ApplicationForm applicationFormId) {
        this.applicationFormId = applicationFormId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterviewImpl interview = (InterviewImpl) o;

        if (mark != interview.mark) return false;
        if (adequateMark != interview.adequateMark) return false;
        if (id != null ? !id.equals(interview.id) : interview.id != null) return false;
        if (date != null ? !date.equals(interview.date) : interview.date != null) return false;
        if (idInterviewer != null ? !idInterviewer.equals(interview.idInterviewer) : interview.idInterviewer != null)
            return false;
        if (interviewerRole != null ? !interviewerRole.equals(interview.interviewerRole) : interview.interviewerRole != null)
            return false;
        return applicationFormId != null ? applicationFormId.equals(interview.applicationFormId) : interview.applicationFormId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + mark;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (idInterviewer != null ? idInterviewer.hashCode() : 0);
        result = 31 * result + (interviewerRole != null ? interviewerRole.hashCode() : 0);
        result = 31 * result + (adequateMark ? 1 : 0);
        result = 31 * result + (applicationFormId != null ? applicationFormId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InterviewImpl{" +
                "id=" + id +
                ", mark=" + mark +
                ", date=" + date +
                ", idInterviewer=" + idInterviewer +
                ", interviewerRole=" + interviewerRole +
                ", adequateMark=" + adequateMark +
                ", applicationFormId=" + applicationFormId +
                '}';
    }
}
