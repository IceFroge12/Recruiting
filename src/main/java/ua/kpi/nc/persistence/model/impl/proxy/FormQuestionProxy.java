package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.List;

/**
 * @author Korzh
 */
public class FormQuestionProxy implements FormQuestion {

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
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public List<FormAnswer> getAnswers() {
        checkFormQuestion();
        return formQuestion.getAnswers();
    }

    @Override
    public void setAnswers(List<FormAnswer> answers) {
        checkFormQuestion();
        formQuestion.setAnswers(answers);
    }

    @Override
    public List<Role> getRoles() {
        checkFormQuestion();
        return formQuestion.getRoles();
    }

    @Override
    public void setRoles(List<Role> roles) {
        checkFormQuestion();
        formQuestion.setRoles(roles);
    }

    @Override
    public String getTitle() {
        checkFormQuestion();
        return formQuestion.getTitle();
    }

    @Override
    public void setTitle(String title) {
        checkFormQuestion();
        formQuestion.setTitle(title);
    }

    public QuestionType getQuestionType() {
        checkFormQuestion();
        return formQuestion.getQuestionType();
    }

    public void setQuestionType(QuestionType questionType) {
        checkFormQuestion();
        formQuestion.setQuestionType(questionType);
    }

    @Override
    public boolean isEnable() {
        checkFormQuestion();
        return formQuestion.isEnable();
    }

    @Override
    public void setEnable(boolean enable) {
        checkFormQuestion();
        formQuestion.setEnable(enable);
    }

    @Override
    public boolean isMandatory() {
        checkFormQuestion();
        return formQuestion.isMandatory();
    }

    @Override
    public void setMandatory(boolean mandatory) {
        checkFormQuestion();
        formQuestion.setMandatory(mandatory);
    }

    @Override
    public List<FormAnswerVariant> getFormAnswerVariants() {
        checkFormQuestion();
        return formQuestion.getFormAnswerVariants();
    }

    @Override
    public void setFormAnswerVariants(List<FormAnswerVariant> formAnswerVariants) {
        checkFormQuestion();
        formQuestion.setFormAnswerVariants(formAnswerVariants);
    }

    private void checkFormQuestion() {
        if (formQuestion == null) {
            formQuestionService = ServiceFactory.getFormQuestionService();
            formQuestion = downloadQuestion();
        }
    }

    private FormQuestionImpl downloadQuestion() {
        return (FormQuestionImpl) formQuestionService.getById(id);
    }
}
