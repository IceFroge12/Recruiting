package ua.kpi.nc.reports.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.reports.Line;
import ua.kpi.nc.service.ReportService;
import ua.kpi.nc.service.ServiceFactory;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 28.04.2016.
 */
public class Poi {
    private BufferedOutputStream bos;
    private Workbook wb;
    private ReportService rs;
    private ArrayList<Object> rows;
    private ArrayList<Line> cells;
    private static Logger log = LoggerFactory.getLogger(Poi.class.getName());

    Poi() {
        wb = new SXSSFWorkbook();
        rs = ServiceFactory.getReportService();
        rows = (ArrayList) rs.getReportOfApproved().getHeader().getCells();
        cells = (ArrayList) rs.getReportOfApproved().getLines();
    }

    public void init(String path) {
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            log.trace("open BufferedOutputStream");
        } catch (FileNotFoundException e) {
            log.error("Cannot initialize OutputStream", e);
        }
    }

    void close() {
        try {
            bos.close();
            log.trace("close BufferedOutputStream");
        } catch (IOException e) {
            log.error("Cannot close OutputStream", e);
        }
    }


    void write(List<Object> objects, List<Line> lines) {

        ArrayList<Object> rows = (ArrayList<Object>) objects;
        ArrayList<Line> cells = (ArrayList<Line>) lines;
        log.trace("rows and cells initialization was successful");
        Workbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < rows.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rows.get(i) + "");
        }
        for (int i = 1; i <= cells.size(); i++) {
            Row roww = sheet.createRow(i);
            for (int j = 0; j < rows.size(); j++) {
                Cell cell = roww.createCell(j);
                cell.setCellValue(cells.get(i - 1).getCells().get(j) + "");
            }
        }
        try {
            wb.write(bos);
            log.trace("Write data into Workbook");
        } catch (IOException e) {
            log.error("Cannot write data into Workbook", e);
        }
    }

    public void writeXLSX(String path) {
        init(path);
        write(rows, cells);
        close();
    }


}

