package ua.kpi.nc.persistence.model.impl.real;

import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.util.Set;

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
    private Set<FormAnswer> answers;
    private Set<Role> roles;

    public FormQuestionImpl() {
    }

    public FormQuestionImpl(String title, QuestionType questionType, boolean enable, boolean mandatory,
                            Set<FormAnswer> answers, Set<Role> roles) {
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
        this.answers = answers;
        this.roles = roles;
    }

    public FormQuestionImpl(Long id, String title, QuestionType questionType, boolean enable, boolean mandatory,
                            Set<FormAnswer> answers, Set<Role> roles) {
        this.id = id;
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
        this.answers = answers;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Set<FormAnswer> getAnswers() {
        return answers;
    }

    @Override
    public void setAnswers(Set<FormAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isMandatory() {
        return mandatory;
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
        if (questionType != null ? !questionType.equals(that.questionType) : that.questionType != null)
            return false;
        if (answers != null ? !answers.equals(that.answers) : that.answers != null) return false;
        return roles != null ? roles.equals(that.roles) : that.roles == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        result = 31 * result + (mandatory ? 1 : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
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
                ", answers=" + answers +
                ", roles=" + roles +
                '}';
    }

}
