package ua.kpi.nc.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariant extends Serializable {

    Long getId();

    void setId(Long id);

    String getAnswer();

    void setAnswer(String answer);

    FormQuestion getIdQuestion();

    void setIdQuestion(FormQuestion idQuestion);

}
