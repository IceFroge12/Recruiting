package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormQuestion extends Serializable {
    Long getId();

    void setId(Long id);

    List<FormAnswer> getAnswers();

    void setAnswers(List<FormAnswer> answers);

    List<Role> getRoles();

    void setRoles(List<Role> roles);

    String getTitle();

    void setTitle(String title);

    QuestionType getQuestionType();

    void setQuestionType(QuestionType questionType);

    boolean isEnable();

    void setEnable(boolean enable);

    boolean isMandatory();

    void setMandatory(boolean mandatory);

    List<FormAnswerVariant> getFormAnswerVariants();

    void setFormAnswerVariants(List<FormAnswerVariant> formAnswerVariants);

}
