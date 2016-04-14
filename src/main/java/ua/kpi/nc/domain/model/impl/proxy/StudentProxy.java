package ua.kpi.nc.domain.model.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.domain.model.SocialAuth;
import ua.kpi.nc.domain.model.Student;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.StudentImpl;
import ua.kpi.nc.service.StudentService;

/**
 * Created by Chalienko on 14.04.2016.
 */
@Configurable
public class StudentProxy implements Student {

    private Long id;

    private StudentImpl student;
    @Autowired
    private StudentService studentService;

    public StudentProxy() {
    }

    public StudentProxy(Long id) {
        this.id = id;
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
    public User getUser() {
        if (student == null) {
            student = downloadStudent();
        }
        return student.getUser();
    }

    @Override
    public void setUser(User user) {
        if (student == null) {
            student = downloadStudent();
        }
        student.setUser(user);
    }

    @Override
    public SocialAuth getSocialAuth() {
        if (student == null) {
            student = downloadStudent();
        }
        return student.getSocialAuth();
    }

    @Override
    public void setSocialAuth(SocialAuth socialAuth) {
        if (student == null) {
            student = downloadStudent();
        }
        student.setSocialAuth(socialAuth);
    }

    @Override
    public String getFeedback() {
        if (student == null) {
            student = downloadStudent();
        }
        return student.getFeedback();
    }

    @Override
    public void setFeedback(String feedback) {
        if (student == null) {
            student = downloadStudent();
        }
        student.setFeedback(feedback);
    }

    @Override
    public String getPhotoPath() {
        if (student == null) {
            student = downloadStudent();
        }
        return student.getPhotoPath();
    }

    @Override
    public void setPhotoPath(String photoPath) {
        if (student == null) {
            student = downloadStudent();
        }
        student.setPhotoPath(photoPath);
    }

    private StudentImpl downloadStudent() {
        return (StudentImpl) studentService.getStudentByID(id);
    }
}
