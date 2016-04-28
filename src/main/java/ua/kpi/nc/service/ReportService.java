package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ReportInfo;
import ua.kpi.nc.reports.Report;

import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public interface ReportService {

    ReportInfo getByID(Long id);
    
    Report getReportById(Long id);
    
    Report getReportOfApproved();

    ReportInfo getByTitle(String title);

    Set<ReportInfo> getAll();

}
