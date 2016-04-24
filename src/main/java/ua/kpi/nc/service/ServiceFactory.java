package ua.kpi.nc.service;

import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.service.impl.*;

/**
 * Created by dima on 20.04.16.
 */
public class ServiceFactory {

    public static UserService getUserService() {
        return new UserServiceImpl(DaoFactory.getUserDao());
    }

    public static RoleService getRoleService() {
        return new RoleServiceImpl(DaoFactory.getRoleDao());
    }

    public static RecruitmentService getRecruitmentService() {
        return new RecruitmentServiceImpl(DaoFactory.getRecruitmentDao());
    }

    public static ApplicationFormService getApplicationFormService() {
        return new ApplicationFormServiceImpl(DaoFactory.getApplicationFormDao());
    }

    public static DecisionService getDecisionService() {
        return new DecisionServiceImpl(DaoFactory.getDecisionDao());
    }

    public static EmailTemplateService getEmailTemplateService() {
        return new EmailTemplateServiceImpl(DaoFactory.getEmailTemplateDao());
    }

    public static FormAnswerService getFormAnswerService() {
        return new FormAnswerServiceImpl(DaoFactory.getFormAnswerDao());
    }

    public static FormAnswerVariantService getFormAnswerVariantService() {
        return new FormAnswerVariantServiceImpl(DaoFactory.getFormAnswerVariantDao(),DaoFactory.getFormQuestionDao());
    }

    public static FormQuestionService getFormQuestionService() {
        return new FormQuestionServiceImpl(DaoFactory.getFormQuestionDao());
    }

    public static InterviewService getInterviewService(){
        return new InterviewServiceImpl(DaoFactory.getInterviewDao());
    }

    public static ScheduleTimePointService getScheduleTimePointService(){
        return new ScheduleTimePointServiceImpl(DaoFactory.getScheduleTimePointDao());
    }

    public static SocialInformationService getSocialInformationService(){
        return new SocialInformationServiceImpl(DaoFactory.getSocialInformationDao());
    }
}
