package ua.kpi.nc.report.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.impl.ReportDaoImpl;
import ua.kpi.nc.persistence.model.impl.proxy.FormQuestionProxy;
import ua.kpi.nc.report.renderer.RendererFactory;
import ua.kpi.nc.report.renderer.ReportRenderer;
import ua.kpi.nc.service.ReportService;
import ua.kpi.nc.service.impl.ReportServiceImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Алексей on 28.04.2016.
 */
public class RenderXLS extends AbsRender {

	public RenderXLS(String filename) {
		this.filename = filename;
		wb = new HSSFWorkbook();
	}

	public RenderXLS() {
		wb = new HSSFWorkbook();
	}

//	public static void main(String [] args) throws FileNotFoundException {
//		ReportRenderer rr = RendererFactory.getXLSRenderer();
//	ReportService rs=new ReportServiceImpl(DaoFactory.getReportDao());
//	rr.render(rs.getReportOfAnswers(new FormQuestionProxy(1L)),new FileOutputStream("qwe.xls"));
//}
}
