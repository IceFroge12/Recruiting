package ua.kpi.nc.persistence.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Korzh
 */
public class ApplicationFormDto {

    private Long ID;
    private String status;
    private UserDto user;
    private List<StudentAppFormQuestionDto> questions = new ArrayList<StudentAppFormQuestionDto>();

    public ApplicationFormDto() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StudentAppFormQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<StudentAppFormQuestionDto> questions) {
        this.questions = questions;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
