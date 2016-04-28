package ua.kpi.nc.report.renderer;

import javax.servlet.http.HttpServletResponse;

import ua.kpi.nc.report.Report;

public interface ReportRenderer {

	void render(Report report, HttpServletResponse response);
	
}
