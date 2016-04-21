package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerVariantImpl;
import ua.kpi.nc.service.FormAnswerVariantService;

/**
 @author Vova Korzh
 **/
public class FormAnswerVariantProxy implements FormAnswerVariant{
    private Long id;
    private FormAnswerVariantImpl formAnswerVariant;

    private FormAnswerVariantService formAnswerVariantService;

    public FormAnswerVariantProxy() {
    }

    public FormAnswerVariantProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return  id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;

    }

    @Override
    public String getAnswer() {
        if (formAnswerVariant == null) {
            formAnswerVariant = downloadRecruitment();
        }
        return formAnswerVariant.getAnswer();
    }

    @Override
    public void setAnswer(String answer) {
        if (formAnswerVariant == null) {
            formAnswerVariant = downloadRecruitment();
        }
        formAnswerVariant.setAnswer(answer);
    }

    @Override
    public FormQuestion getIdQuestion() {
        if (formAnswerVariant == null) {
            formAnswerVariant = downloadRecruitment();
        }
        return formAnswerVariant.getIdQuestion();
    }

    @Override
    public void setIdQuestion(FormQuestion idQuestion) {
        if (formAnswerVariant == null) {
            formAnswerVariant = downloadRecruitment();
        }
        formAnswerVariant.setIdQuestion(idQuestion);
    }

    private FormAnswerVariantImpl downloadRecruitment() {
        return (FormAnswerVariantImpl) formAnswerVariantService.getFormAnswerVariantById(id);
    }
}
