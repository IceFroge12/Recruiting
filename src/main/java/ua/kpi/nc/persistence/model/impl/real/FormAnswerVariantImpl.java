package ua.kpi.nc.persistence.model.impl.real;

import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormAnswerVariantImpl implements FormAnswerVariant {

    private Long id;
    private String title;
    private FormQuestion idQuestion;

    public FormAnswerVariantImpl() {
    }

    public FormAnswerVariantImpl(Long id, String title, FormQuestion idQuestion) {
        this.id = id;
        this.title = title;
        this.idQuestion = idQuestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FormQuestion getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(FormQuestion idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormAnswerVariantImpl that = (FormAnswerVariantImpl) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return idQuestion != null ? idQuestion.equals(that.idQuestion) : that.idQuestion == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (idQuestion != null ? idQuestion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormAnswerVariantImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idQuestion=" + idQuestion +
                '}';
    }
}
