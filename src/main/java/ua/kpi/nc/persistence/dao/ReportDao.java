package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.report.Report;

import java.util.List;
import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public interface ReportDao {
	ReportInfo getByID(Long id);

	ReportInfo getByTitle(String title);

	Set<ReportInfo> getAll();

	Long insertReport(ReportInfo report);

	int updateReport(ReportInfo report);

	int deleteReport(ReportInfo report);

	Report getReportOfApproved();

	Report getReportOfAnswers(Long questionId, List<Recruitment> recruitments);
}
