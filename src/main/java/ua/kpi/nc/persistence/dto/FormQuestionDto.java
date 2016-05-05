package ua.kpi.nc.persistence.dto;

import ua.kpi.nc.persistence.model.Role;

import java.util.List;

/**
 * Created by dima on 04.05.16.
 */
public class FormQuestionDto {

    private String question;
    private String type;
    private boolean enable;
    private List<String> formAnswerVariants;
    private String role;

    public FormQuestionDto() {
    }

    public FormQuestionDto(String question, String type, boolean enable, List<String> formAnswerVariants, String role) {
        this.question = question;
        this.type = type;
        this.enable = enable;
        this.formAnswerVariants = formAnswerVariants;
        this.role = role;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getFormAnswerVariants() {
        return formAnswerVariants;
    }

    public void setFormAnswerVariants(List<String> formAnswerVariants) {
        this.formAnswerVariants = formAnswerVariants;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "FormQuestionDto{" +
                "question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", enable=" + enable +
                ", formAnswerVariants=" + formAnswerVariants +
                ", role=" + role +
                '}';
    }
}
