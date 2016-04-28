package ua.kpi.nc.service.impl;

import java.util.Set;

import ua.kpi.nc.persistence.dao.ReportDao;
import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.report.Report;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ReportService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by Nikita on 24.04.2016.
 */
public class ReportServiceImpl implements ReportService {
	private ReportDao reportDao;

	public ReportServiceImpl(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public ReportInfo getByID(Long id) {
		return reportDao.getByID(id);
	}

	@Override
	public ReportInfo getByTitle(String title) {
		return reportDao.getByTitle(title);
	}

	@Override
	public Set<ReportInfo> getAll() {
		return reportDao.getAll();
	}

	@Override
	public Report getReportOfApproved() {
		return reportDao.getReportOfApproved();
	}

	@Override
	public Report getReportOfAnswers(Long questionId) {
		RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
		return reportDao.getReportOfAnswers(questionId, recruitmentService.getAll());
	}

}
