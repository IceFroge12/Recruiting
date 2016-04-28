package ua.kpi.nc.reports.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import ua.kpi.nc.reports.Line;
import ua.kpi.nc.service.ReportService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by Алексей on 28.04.2016.
 */
public class Poi {
        Workbook wb=new SXSSFWorkbook();
        Sheet sheet=wb.createSheet();

        ReportService rs= ServiceFactory.getReportService();


}
