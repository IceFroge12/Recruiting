package ua.kpi.nc.report.poi;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import ua.kpi.nc.report.Report;

/**
 * Created by Алексей on 28.04.2016.
 */
public class RenderXLSX extends AbsRender {

	public RenderXLSX(String filename) {
		this.filename = filename;
		wb = new SXSSFWorkbook();
	}

	@Override
	public void render(Report report, HttpServletResponse response) {
		String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + filename + "\""));
		super.render(report, response);
	}

}
