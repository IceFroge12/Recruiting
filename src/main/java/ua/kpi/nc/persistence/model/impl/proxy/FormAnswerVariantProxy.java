package ua.kpi.nc.persistence.model.impl.proxy;

import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerVariantImpl;
import ua.kpi.nc.service.FormAnswerVariantService;
import ua.kpi.nc.service.ServiceFactory;

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
        checkFormAnswerVariant();
        return formAnswerVariant.getAnswer();
    }

    @Override
    public void setAnswer(String answer) {
        checkFormAnswerVariant();
        formAnswerVariant.setAnswer(answer);
    }

    public FormQuestion getFormQuestion() {
        checkFormAnswerVariant();
        return formAnswerVariant.getFormQuestion();
    }

    public void setFormQuestion(FormQuestion formQuestion) {
        checkFormAnswerVariant();
        formAnswerVariant.setFormQuestion(formQuestion);
    }

    private void checkFormAnswerVariant(){
        if (formAnswerVariant == null) {
            formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
            formAnswerVariant = downloadRecruitment();
        }
    }
    private FormAnswerVariantImpl downloadRecruitment() {
        return (FormAnswerVariantImpl) formAnswerVariantService.getAnswerVariantById(id);
    }
}
