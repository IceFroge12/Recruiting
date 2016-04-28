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

    public void init(String path) {
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wb = new SXSSFWorkbook();
        rs = ServiceFactory.getReportService();
    }

    void close() {
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void write(List<Object> objects, List<Line> lines) {

        ArrayList<Object> list1 = (ArrayList<Object>) objects;
        ArrayList<Line> list2 = (ArrayList) lines;

        //  ArrayList<Object> list1 = (ArrayList) rs.getReportOfApproved().getHeader().getCells();
        //  ArrayList<Line> list2 = (ArrayList) rs.getReportOfApproved().getLines();


        Workbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < list1.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(list1.get(i) + "");
        }
        for (int i = 1; i <= list2.size(); i++) {
            Row roww = sheet.createRow(i);
            for (int j = 0; j < list1.size(); j++) {
                Cell cell = roww.createCell(j);
                cell.setCellValue(list2.get(i - 1).getCells().get(j) + "");
            }
        }
        try {
            wb.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

