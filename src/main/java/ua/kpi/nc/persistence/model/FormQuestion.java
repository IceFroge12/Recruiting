package ua.kpi.nc.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormQuestion extends Serializable {
    Long getId();

    void setId(Long id);

    String getTitle();

    void setTitle(String title);

    QuestionType getQuestionType();

    void setQuestionType(QuestionType questionType);

    boolean isEnable();

    void setEnable(boolean enable);

    boolean isMandatory();

    void setMandatory(boolean mandatory);
}
