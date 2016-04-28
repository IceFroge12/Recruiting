package ua.kpi.nc.reports.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import ua.kpi.nc.reports.Line;
import ua.kpi.nc.service.ReportService;
import ua.kpi.nc.service.ServiceFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Алексей on 28.04.2016.
 */
public class Poi {
    static void m1(){
        ReportService rs = ServiceFactory.getReportService();

        ArrayList<Object> list1 = (ArrayList) rs.getReportOfApproved().getHeader().getCells();
        ArrayList<Line> list2 = (ArrayList) rs.getReportOfApproved().getLines();


        Workbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < list1.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(list1.get(i) + "");
        }


        for (int i = 1; i <=list2.size(); i++) {
            Row roww = sheet.createRow(i);
            for (int j = 0; j < list1.size(); j++) {
                Cell cell = roww.createCell(j);
                cell.setCellValue(list2.get(i - 1).getCells().get(j) + "");
            }
        }

        try (FileOutputStream fos = new FileOutputStream("qwerty.xlsx")) {
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

