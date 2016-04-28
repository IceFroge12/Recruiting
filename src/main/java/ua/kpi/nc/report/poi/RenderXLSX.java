package ua.kpi.nc.report.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Created by Алексей on 28.04.2016.
 */
public class RenderXLSX extends AbsRender {


    public RenderXLSX(String filename) {
        this.filename=filename;
        wb = new SXSSFWorkbook();
    }


}
