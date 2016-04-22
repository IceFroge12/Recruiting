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

    public FormQuestion getFormQuestion() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getFormQuestion();
    }

    public void setFormQuestion(FormQuestion formQuestion) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setFormQuestion(formQuestion);
    }

    public ApplicationForm getApplicationForm() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getApplicationForm();
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setApplicationForm(applicationForm);
    }

    public FormAnswerVariant getFormAnswerVariant() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getFormAnswerVariant();
    }

    public void setFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setFormAnswerVariant(formAnswerVariant);
    }

    public Interview getInterview() {
        if (formAnswer == null) {
            formAnswer =downloadFormAnswer();
        }
        return formAnswer.getInterview();
    }

    public void setInterview(Interview interview) {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
        formAnswer.setInterview(interview);
    }
    private FormAnswerImpl downloadFormAnswer() {
        return (FormAnswerImpl) answerService.getFormAnswerByID(id);
    }
}
