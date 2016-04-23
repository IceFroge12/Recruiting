package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.FormQuestionType;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.ServiceFactory;

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
        checkFormQuestion();
        return formQuestion.getTitle();
    }

    public FormQuestionType getFormQuestionType() {
        checkFormQuestion();
        return formQuestion.getFormQuestionType();
    }

    @Override
    public boolean isEnable() {
        checkFormQuestion();
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
        checkFormQuestion();
        formQuestion.setTitle(title);
    }

    public void setFormQuestionType(FormQuestionType formQuestionType) {
        checkFormQuestion();
        formQuestion.setFormQuestionType(formQuestionType);
    }

    @Override
    public void setEnable(boolean enable) {
        checkFormQuestion();formQuestion.setEnable(enable);
    }

    @Override
    public void setMandatory(boolean mandatory) {
        checkFormQuestion();
        formQuestion.setMandatory(mandatory);
    }

    private void checkFormQuestion(){
        if (formQuestion == null) {
            formQuestionService = ServiceFactory.getFormQuestionService();
            formQuestion = downloadQuestion();
        }
    }
    private FormQuestionImpl downloadQuestion() {
        return (FormQuestionImpl) formQuestionService.getFormQuestionById(id);
    }
}
