package ua.kpi.nc.service;

import java.util.Set;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.report.Report;

/**
 * Created by Nikita on 24.04.2016.
 */
public interface ReportService {

    ReportInfo getByID(Long id);
    
    Report getReportOfApproved();
    
    Report getReportOfAnswers(FormQuestion question);

    ReportInfo getByTitle(String title);

    Set<ReportInfo> getAll();

}
