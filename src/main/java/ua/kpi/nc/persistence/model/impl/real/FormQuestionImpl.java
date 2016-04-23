package ua.kpi.nc.persistence.model.impl.real;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormQuestionImpl implements FormQuestion {

    private static final long serialVersionUID = -4875241221362139428L;
    private Long id;
    private String title;
    private QuestionType questionType;
    private boolean enable;
    private boolean mandatory;

    public FormQuestionImpl() {
    }

    public FormQuestionImpl(String title, QuestionType questionType, boolean enable, boolean mandatory) {
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
    }

    public FormQuestionImpl(Long id, String title, QuestionType questionType, boolean enable, boolean mandatory) {
        this.id = id;
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormQuestionImpl that = (FormQuestionImpl) o;

        if (enable != that.enable) return false;
        if (mandatory != that.mandatory) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return questionType != null ? questionType.equals(that.questionType) : that.questionType == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        result = 31 * result + (mandatory ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormQuestionImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionType=" + questionType +
                ", enable=" + enable +
                ", mandatory=" + mandatory +
                '}';
    }
}
