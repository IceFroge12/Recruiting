package ua.kpi.nc.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswer extends Serializable {
    public Long getId();


    public void setId(Long id);

    public String getAnswer();

    public void setAnswer(String answer);

    public FormQuestion getIdQuestion();

    public void setIdQuestion(FormQuestion idQuestion);

    public ApplicationForm getIdApplicationForm();

    public void setIdApplicationForm(ApplicationForm idApplicationForm);

    public FormAnswerVariant getIdVariant();

    public void setIdVariant(FormAnswerVariant idVariant);

    public Interview getIdInterview();

    public void setIdInterview(Interview idInterview);
}
