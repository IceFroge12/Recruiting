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

    public FormQuestion getFormQuestion();

    public void setFormQuestion(FormQuestion formQuestion);

    public ApplicationForm getApplicationForm();

    public void setApplicationForm(ApplicationForm applicationForm);

    public FormAnswerVariant getFormAnswerVariant();

    public void setFormAnswerVariant(FormAnswerVariant formAnswerVariant);

    public Interview getInterview();

    public void setInterview(Interview interview);
}
