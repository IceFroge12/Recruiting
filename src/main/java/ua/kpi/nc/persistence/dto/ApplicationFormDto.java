package ua.kpi.nc.persistence.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Korzh
 */
public class ApplicationFormDto {

    private long id;
    private String status;
    private UserDto user;
    private List<StudentAppFormQuestionDto> questions = new ArrayList<>();

    public ApplicationFormDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

	@Override
	public String toString() {
		return "ApplicationFormDto [id=" + id + ", status=" + status + ", user=" + user + ", questions=" + questions
				+ "]";
	}
}
