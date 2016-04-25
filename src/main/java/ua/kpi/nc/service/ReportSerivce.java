package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.Report;

import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public interface ReportSerivce {

    Report getByID(Long id);

    Report getByTitle(String title);

    Set<Report> getAll();

    Long insertReport(Report report);


    int updateReport(Report report);

    int deleteReport(Report report);
}
