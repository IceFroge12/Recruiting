package ua.kpi.nc.report.renderer;

import java.io.OutputStream;

import ua.kpi.nc.report.Report;

public interface ReportRenderer {

	public void render(Report report, OutputStream out);

}
