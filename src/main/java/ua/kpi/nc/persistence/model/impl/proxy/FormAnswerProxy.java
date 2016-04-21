package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.service.FormAnswerService;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormAnswerProxy implements FormAnswer{
    private Long id;

    private FormAnswerImpl formAnswer;

    private FormAnswerService answerService;

    public FormAnswerProxy() {
    }

    public FormAnswerProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    @Override
    public String getAnswer() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getAnswer();
    }

    @Override
    public void setAnswer(String answer) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setAnswer(answer);
    }

    @Override
    public FormQuestion getIdQuestion() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getIdQuestion();
    }

    @Override
    public void setIdQuestion(FormQuestion idQuestion) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setIdQuestion(idQuestion);
    }

    @Override
    public ApplicationForm getIdApplicationForm() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getIdApplicationForm();
    }

    @Override
    public void setIdApplicationForm(ApplicationForm idApplicationForm) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setIdApplicationForm(idApplicationForm);
    }

    @Override
    public FormAnswerVariant getIdVariant() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getIdVariant();
    }

    @Override
    public void setIdVariant(FormAnswerVariant idVariant) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setIdVariant(idVariant);
    }

    @Override
    public Interview getIdInterview() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getIdInterview();
    }

    @Override
    public void setIdInterview(Interview idInterview) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setIdInterview(idInterview);
    }
    private FormAnswerImpl downloadFormAnswer() {
        return (FormAnswerImpl) answerService.getFormAnswerByID(id);
    }
}
