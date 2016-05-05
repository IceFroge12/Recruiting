package ua.kpi.nc.persistence.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Korzh
 */
public class StudentAppFormQuestionDto {
    private String questionTitle;
    private String questionType;
    private boolean isMandatory;
    private List<StudentAnswerDto> answers = new ArrayList<StudentAnswerDto>();
    private List<QuestionVariantDto> variants = new ArrayList<QuestionVariantDto>();

    public StudentAppFormQuestionDto() {
    }

    public StudentAppFormQuestionDto(String questionTitle, String questionType, boolean isMandatory, List<StudentAnswerDto> answers, List<QuestionVariantDto> variants) {
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.isMandatory = isMandatory;
        this.answers = answers;
        this.variants = variants;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public List<StudentAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswerDto> answers) {
        this.answers = answers;
    }

    public List<QuestionVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<QuestionVariantDto> variants) {
        this.variants = variants;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionTitle='" + questionTitle + '\'' +
                ", questionType='" + questionType + '\'' +
                ", isMandatory=" + isMandatory +
                ", answers=" + answers +
                ", variants=" + variants +
                '}';
    }
}
