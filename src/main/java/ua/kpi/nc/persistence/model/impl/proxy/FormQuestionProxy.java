package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.FormQuestionService;

/**
 @author Korzh
 */
public class FormQuestionProxy implements FormQuestion{

    private Long id;
    private FormQuestionImpl formQuestion;

    private FormQuestionService formQuestionService;

    public FormQuestionProxy() {
    }

    public FormQuestionProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        return formQuestion.getTitle();
    }

    public QuestionType getQuestionType() {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        return formQuestion.getQuestionType();
    }

    @Override
    public boolean isEnable() {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        return formQuestion.isEnable();
    }

    @Override
    public boolean isMandatory() {
        return formQuestion.isMandatory();
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setTitle(String title) {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        formQuestion.setTitle(title);
    }

    public void setQuestionType(QuestionType questionType) {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        formQuestion.setQuestionType(questionType);
    }

    @Override
    public void setEnable(boolean enable) {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        formQuestion.setEnable(enable);
    }

    @Override
    public void setMandatory(boolean mandatory) {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        formQuestion.setMandatory(mandatory);
    }
    private FormQuestionImpl downloadQuestion() {
        return (FormQuestionImpl) formQuestionService.getFormQuestionById(id);
    }
}
