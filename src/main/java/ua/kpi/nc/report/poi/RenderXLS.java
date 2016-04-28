package ua.kpi.nc.report.poi;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import ua.kpi.nc.report.Report;

/**
 * Created by Алексей on 28.04.2016.
 */
public class RenderXLS extends AbsRender {

	public RenderXLS(String filename) {
		this.filename = filename;
		wb = new HSSFWorkbook();
	}

	@Override
	public void render(Report report, HttpServletResponse response) {
		String mimeType = "application/vnd.ms-excel";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + filename + "\""));
		super.render(report, response);
	}
}
