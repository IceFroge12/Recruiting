package ua.kpi.nc.service;

import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.service.impl.ApplicationFormServiceImpl;
import ua.kpi.nc.service.impl.DecisionServiceImpl;
import ua.kpi.nc.service.impl.EmailTemplateServiceImpl;
import ua.kpi.nc.service.impl.RecruitmentServiceImpl;
import ua.kpi.nc.service.impl.RoleServiceImpl;
import ua.kpi.nc.service.impl.UserServiceImpl;

/**
 * Created by dima on 20.04.16.
 */
public class ServiceFactory {

	public static UserService getUserService() {
		return new UserServiceImpl(DaoFactory.getUserDao());
	}

	public static RoleService getRoleService() {
		return new RoleServiceImpl();
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
}
