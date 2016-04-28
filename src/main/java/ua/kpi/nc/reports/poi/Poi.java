package ua.kpi.nc.reports.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

    public void init(String path) {
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wb = new SXSSFWorkbook();
        rs = ServiceFactory.getReportService();
        rows = (ArrayList) rs.getReportOfApproved().getHeader().getCells();
        cells = (ArrayList) rs.getReportOfApproved().getLines();
    }

    void close() {
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void write(List<Object> objects, List<Line> lines) {

        ArrayList<Object> rows = (ArrayList<Object>) objects;
        ArrayList<Line> cells = (ArrayList) lines;


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeXLSX(String path) {
        init(path);
        write(rows, cells);
        close();
    }




}

