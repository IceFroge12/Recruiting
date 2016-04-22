package ua.kpi.nc.persistence.model.impl.real;

import ua.kpi.nc.persistence.model.*;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormAnswerImpl implements FormAnswer {

    private static final long serialVersionUID = 7004025676148335072L;
    private Long id;
    private String answer;
    private FormQuestion idQuestion;
    private ApplicationForm idApplicationForm;
    private FormAnswerVariant idVariant;
    private Interview idInterview;

    public FormAnswerImpl() {
    }

    public FormAnswerImpl(Long id, String answer, FormQuestion idQuestion, ApplicationForm idApplicationForm, FormAnswerVariant idVariant, Interview idInterview) {
        this.id = id;
        this.answer = answer;
        this.idQuestion = idQuestion;
        this.idApplicationForm = idApplicationForm;
        this.idVariant = idVariant;
        this.idInterview = idInterview;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FormQuestion getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(FormQuestion idQuestion) {
        this.idQuestion = idQuestion;
    }

    public ApplicationForm getIdApplicationForm() {
        return idApplicationForm;
    }

    public void setIdApplicationForm(ApplicationForm idApplicationForm) {
        this.idApplicationForm = idApplicationForm;
    }

    public FormAnswerVariant getIdVariant() {
        return idVariant;
    }

    public void setIdVariant(FormAnswerVariant idVariant) {
        this.idVariant = idVariant;
    }

    public Interview getIdInterview() {
        return idInterview;
    }

    public void setIdInterview(Interview idInterview) {
        this.idInterview = idInterview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormAnswerImpl that = (FormAnswerImpl) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (idQuestion != null ? !idQuestion.equals(that.idQuestion) : that.idQuestion != null) return false;
        if (idApplicationForm != null ? !idApplicationForm.equals(that.idApplicationForm) : that.idApplicationForm != null)
            return false;
        if (idVariant != null ? !idVariant.equals(that.idVariant) : that.idVariant != null) return false;
        return idInterview != null ? idInterview.equals(that.idInterview) : that.idInterview == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (idQuestion != null ? idQuestion.hashCode() : 0);
        result = 31 * result + (idApplicationForm != null ? idApplicationForm.hashCode() : 0);
        result = 31 * result + (idVariant != null ? idVariant.hashCode() : 0);
        result = 31 * result + (idInterview != null ? idInterview.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormAnswerImpl{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", idQuestion=" + idQuestion +
                ", idApplicationForm=" + idApplicationForm +
                ", idVariant=" + idVariant +
                ", idInterview=" + idInterview +
                '}';
    }
}
