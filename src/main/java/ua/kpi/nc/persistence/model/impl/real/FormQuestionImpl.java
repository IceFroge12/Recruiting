package ua.kpi.nc.persistence.model.impl.real;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.FormQuestionType;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormQuestionImpl implements FormQuestion{
    private Long id;
    private String title;
    private FormQuestionType idQuestionType;
    private boolean enable;
    private boolean mandatory;

    public FormQuestionImpl() {
    }

    public FormQuestionImpl(Long id, String title, FormQuestionType idQuestionType, boolean enable, boolean mandatory) {
        this.id = id;
        this.title = title;
        this.idQuestionType = idQuestionType;
        this.enable = enable;
        this.mandatory = mandatory;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public FormQuestionType getIdQuestionType() {
        return idQuestionType;
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

    public void setIdQuestionType(FormQuestionType idQuestionType) {
        this.idQuestionType = idQuestionType;
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
        return idQuestionType != null ? idQuestionType.equals(that.idQuestionType) : that.idQuestionType == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (idQuestionType != null ? idQuestionType.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        result = 31 * result + (mandatory ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormQuestionImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idQuestionType=" + idQuestionType +
                ", enable=" + enable +
                ", mandatory=" + mandatory +
                '}';
    }
}
