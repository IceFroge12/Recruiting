package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.ReportDao;
import ua.kpi.nc.persistence.model.Report;
import ua.kpi.nc.service.ReportSerivce;

import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public class ReportServiceImpl implements ReportSerivce {
    ReportDao reportDao;

    @Override
    public Report getByID(Long id) {
        return reportDao.getByID(id);
    }

    @Override
    public Report getByTitle(String title) {
        return reportDao.getByTitle(title);
    }

    @Override
    public Set<Report> getAll() {
        return reportDao.getAll();
    }

    @Override
    public Long insertReport(Report report) {
        return reportDao.insertReport(report);
    }

    @Override
    public int updateReport(Report report) {
        return reportDao.updateReport(report);
    }

    @Override
    public int deleteReport(Report report) {
        return reportDao.deleteReport(report);
    }
}
