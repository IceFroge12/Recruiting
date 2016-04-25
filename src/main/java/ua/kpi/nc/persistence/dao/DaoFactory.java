package ua.kpi.nc.persistence.dao;


import ua.kpi.nc.persistence.dao.impl.*;

/**
 * Created by Chalienko on 20.04.2016.
 */
public class DaoFactory {
    public static UserDao getUserDao(){
        return new UserDaoImpl(DataSourceFactory.getInstance());
    }

    public static RecruitmentDAO getRecruitmentDao(){
        return new RecruitmentDaoImpl(DataSourceFactory.getInstance());
    }

    public static ReportDao getReportDao() {return new ReportDaoImpl(DataSourceFactory.getInstance());}

    public static EmailTemplateDao getEmailTemplateDao(){return new EmailTemplateDaoImpl(DataSourceFactory.getInstance());}
    
    public static ApplicationFormDao getApplicationFormDao(){return new ApplicationFormDaoImpl(DataSourceFactory.getInstance());}
    
    public static DecisionDao getDecisionDao(){return new DecisionDaoImpl(DataSourceFactory.getInstance());}
    
    public static FormAnswerVariantDao getFormAnswerVariantDao(){return new FormAnswerVariantDaoImpl(DataSourceFactory.getInstance());}

    public static InterviewDao getInterviewDao(){return new InterviewDaoImpl(DataSourceFactory.getInstance());}

    public static FormQuestionDao getFormQuestionDao(){return new FormQuestionDaoImpl(DataSourceFactory.getInstance());}

    public static FormAnswerDao getFormAnswerDao(){return  new FormAnswerDaoImpl(DataSourceFactory.getInstance());}
    
    public static StatusDao getStatusDao(){return new StatusDaoImpl(DataSourceFactory.getInstance());}
    
    public static ScheduleTimePointDao getScheduleTimePointDao(){return new ScheduleTimePointDaoImpl(DataSourceFactory.getInstance());}
    
    public static NotificationTypeDao getNotificationTypeDao(){return new NotificationTypeDaoImpl(DataSourceFactory.getInstance());}
    
    public static TimePriorityTypeDao getTimePriorityDao(){return new TimePriorityTypeDaoImpl(DataSourceFactory.getInstance());}
    
    public static RoleDao getRoleDao(){return new RoleDaoImpl(DataSourceFactory.getInstance());}
    
    public static SocialNetworkDao getSocialNetworkDao(){return new SocialNetworkDaoImpl(DataSourceFactory.getInstance());}
    
    public static SocialInformationDao getSocialInformationDao() {return new SocialInformationDaoImpl(DataSourceFactory.getInstance());}

    public static UserTimePriorityDao getUserTimePriorityDao() {return new UserTimePriorityDaoImpl(DataSourceFactory.getInstance());}
}

