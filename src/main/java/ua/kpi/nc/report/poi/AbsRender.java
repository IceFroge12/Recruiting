package ua.kpi.nc.report.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.report.Line;
import ua.kpi.nc.report.Report;
import ua.kpi.nc.report.renderer.ReportRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Алексей on 28.04.2016.
 */


public abstract class AbsRender implements ReportRenderer {
    protected Workbook wb;
    protected List<Object> header;
    protected List<Line> lines;
    protected static Logger log = LoggerFactory.getLogger(AbsRender.class.getName());

    private void autoSize(Sheet sheet, int columnSize) {
        if (sheet instanceof SXSSFSheet) {
            SXSSFSheet sxSheet = (SXSSFSheet) sheet;
            sxSheet.trackAllColumnsForAutoSizing();
        }
        sheet.autoSizeColumn(columnSize);
    }

    private void writeHeader(Sheet sheet, List<Object> header) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < header.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header.get(i) + "");
            autoSize(sheet, i);
        }
    }

    private void writeLines(Sheet sheet, List<Line> lines) {
        for (int i = 1; i <= lines.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < lines.get(0).getCells().size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(lines.get(i - 1).getCells().get(j) + "");
            }
        }
    }

    @Override
    public void render(Report report, OutputStream out) {
        header = report.getHeader().getCells();
        lines = report.getLines();
        write(header, lines, out);
    }

    protected void write(List<Object> header, List<Line> lines, OutputStream out) {
        Sheet sheet = wb.createSheet();
        writeHeader(sheet, header);
        writeLines(sheet, lines);
        try {
            wb.write(out);
            log.trace("Write data into Workbook was successful");
        } catch (IOException e) {
            log.error("Cannot write data into Workbook", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                    log.trace("OutputStream closed.");
                } catch (IOException e) {
                    log.error("Cannot close OutputStream", e);
                }
            }
        }
    }

}
