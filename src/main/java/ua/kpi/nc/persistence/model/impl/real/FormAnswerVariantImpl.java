package ua.kpi.nc.persistence.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormAnswerVariantImpl implements FormAnswerVariant {

    private static final long serialVersionUID = 1091069075594065071L;
    private Long id;
    private String answer;
    private FormQuestion idQuestion;

    public FormAnswerVariantImpl() {
    }

    public FormAnswerVariantImpl(Long id, String title, FormQuestion idQuestion) {
        this.id = id;
        this.answer = title;
        this.idQuestion = idQuestion;
    }
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String getAnswer() {
        return answer;
    }
    @Override
    public void setAnswer(String title) {
        this.answer = title;
    }
    @Override
    public FormQuestion getIdQuestion() {
        return idQuestion;
    }
    @Override
    public void setIdQuestion(FormQuestion idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FormAnswerVariantImpl that = (FormAnswerVariantImpl) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(answer, that.answer)
                .append(idQuestion, that.idQuestion)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(answer)
                .append(idQuestion)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "FormAnswerVariantImpl{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", idQuestion=" + idQuestion +
                '}';
    }
}
