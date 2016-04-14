package ua.kpi.nc.domain.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.domain.model.SocialAuth;
import ua.kpi.nc.domain.model.Student;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.type.StudentStatusEnum;

/**
 * Created by Chalienko on 14.04.2016.
 */
public class StudentImpl implements Student {

    private Long id;

    private User user;

    private SocialAuth socialAuth;

    private StudentStatusEnum studentStatus;

    private String feedback;

    private String photoPath;

    public StudentImpl() {
    }

    public StudentImpl(Long id, User user, SocialAuth socialAuth, String feedback, String photoPath) {
        this.id = id;
        this.user = user;
        this.socialAuth = socialAuth;
        this.feedback = feedback;
        this.photoPath = photoPath;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocialAuth getSocialAuth() {
        return socialAuth;
    }

    public void setSocialAuth(SocialAuth socialAuth) {
        this.socialAuth = socialAuth;
    }

    public StudentStatusEnum getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatusEnum studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "user=" + user.getFirstName() + " " + user.getLastName() + " " +
                ", studentStatus=" + studentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StudentImpl student = (StudentImpl) o;

        return new EqualsBuilder()
                .append(id, student.id)
                .append(user, student.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(user)
                .toHashCode();
    }
}

