package ua.kpi.nc.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariant extends Serializable {

    public Long getId();

    public void setId(Long id);

    public String getTitle();

    public void setTitle(String title);

    public FormQuestion getIdQuestion();

    public void setIdQuestion(FormQuestion idQuestion);

}
