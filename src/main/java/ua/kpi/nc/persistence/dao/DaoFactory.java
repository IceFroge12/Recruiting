package ua.kpi.nc.persistence.dao;


import ua.kpi.nc.persistence.dao.impl.*;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DaoFactory {
    public static UserDao getUserDao() {
        return new UserDaoImpl(DataSourceSingleton.getInstance());
    }

    public static RecruitmentDAO getRecruitmentDao() {
        return new RecruitmentDaoImpl(DataSourceSingleton.getInstance());
    }

    public static ReportDao getReportDao() {
        return new ReportDaoImpl(DataSourceSingleton.getInstance());
    }

    public static EmailTemplateDao getEmailTemplateDao() {
        return new EmailTemplateDaoImpl(DataSourceSingleton.getInstance());
    }

    public static ApplicationFormDao getApplicationFormDao() {
        return new ApplicationFormDaoImpl(DataSourceSingleton.getInstance());
    }

    public static DecisionDao getDecisionDao() {
        return new DecisionDaoImpl(DataSourceSingleton.getInstance());
    }
    
    public static FormAnswerVariantDao getFormAnswerVariantDao() {
        return new FormAnswerVariantDaoImpl(DataSourceSingleton.getInstance());
    }

    public static InterviewDao getInterviewDao() {
        return new InterviewDaoImpl(DataSourceSingleton.getInstance());
    }

    public static FormQuestionDao getFormQuestionDao() {
        return new FormQuestionDaoImpl(DataSourceSingleton.getInstance());
    }

    public static FormAnswerDao getFormAnswerDao() {
        return new FormAnswerDaoImpl(DataSourceSingleton.getInstance());
    }

    public static StatusDao getStatusDao() {
        return new StatusDaoImpl(DataSourceSingleton.getInstance());
    }

    public static ScheduleTimePointDao getScheduleTimePointDao() {
        return new ScheduleTimePointDaoImpl(DataSourceSingleton.getInstance());
    }

    public static NotificationTypeDao getNotificationTypeDao() {
        return new NotificationTypeDaoImpl(DataSourceSingleton.getInstance());
    }

    public static TimePriorityTypeDao getTimePriorityDao() {
        return new TimePriorityTypeDaoImpl(DataSourceSingleton.getInstance());
    }

    public static RoleDao getRoleDao() {
        return new RoleDaoImpl(DataSourceSingleton.getInstance());
    }

    public static SocialNetworkDao getSocialNetworkDao() {
        return new SocialNetworkDaoImpl(DataSourceSingleton.getInstance());
    }

    public static SocialInformationDao getSocialInformationDao() {
        return new SocialInformationDaoImpl(DataSourceSingleton.getInstance());
    }

    public static UserTimePriorityDao getUserTimePriorityDao() {
        return new UserTimePriorityDaoImpl(DataSourceSingleton.getInstance());
    }

    public static QuestionTypeDao getQuestionTypeDao() {
        return new QuestionTypeDaoImpl(DataSourceSingleton.getInstance());
    }

    public static ResendMessageDao getResendMessageDao() {
        return new ResendMessageDaoImpl(DataSourceSingleton.getInstance());
    }
}

