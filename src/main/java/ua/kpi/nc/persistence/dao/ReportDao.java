package ua.kpi.nc.persistence.dao;

import java.util.List;
import java.util.Set;

import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.report.Line;

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

	List<Line> extractWithMetaData(ReportInfo reportInfo);

	Line getAnswerVariantLine(ReportInfo reportInfo, FormQuestion question, FormAnswerVariant formAnswerVariant);
}
