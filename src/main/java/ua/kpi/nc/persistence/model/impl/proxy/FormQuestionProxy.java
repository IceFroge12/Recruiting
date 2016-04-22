package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.FormQuestionType;
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

    @Override
    public FormQuestionType getIdQuestionType() {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        return formQuestion.getIdQuestionType();
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

    @Override
    public void setIdQuestionType(FormQuestionType idQuestionType) {
        if (formQuestion == null) {
            formQuestion = downloadQuestion();
        }
        formQuestion.setIdQuestionType(idQuestionType);
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
