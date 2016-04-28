package ua.kpi.nc.reports;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by Алексей on 27.04.2016.
 */
public class Reports {
    private FileInputStream input;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private DefaultCategoryDataset dataset;
    private String filename;
    private final static int width = 450;
    private final static int height = 290;

    Reports(String filename){
        this.filename=filename;
    }

    void writeData(Object... obj){
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();
        //....Row row = ..
        try(FileOutputStream output = new FileOutputStream(filename)) {
            workbook.write(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void readData(){
        /*try (FileInputStream input = new FileInputStream(new File(filename))){
            workbook = new HSSFWorkbook(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        dataset = new DefaultCategoryDataset();
        Iterator<Row> rowIterator = sheet.iterator();
        String chart_label = "";
        Number chart_data = 0;
        String chart_year = "";
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            chart_year = new SimpleDateFormat("YYYY").format(cell.getDateCellValue());
                        } else {
                            chart_data = cell.getNumericCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        chart_label = cell.getStringCellValue();
                        break;
                }
            }
            dataset.addValue(chart_data.doubleValue(), chart_label, chart_year);
        }*/
    }

    DefaultCategoryDataset getDataSet() {
        return dataset;
    }

    void writeDiagram(){
        JFreeChart BarChartObject = ChartFactory.createBarChart("", "students", "number", getDataSet(), PlotOrientation.VERTICAL, true, true, false);


        try (ByteArrayOutputStream chart_out = new ByteArrayOutputStream();){
            ChartUtilities.writeChartAsPNG(chart_out, BarChartObject, width, height);
            int my_picture_id = workbook.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_PNG);
            HSSFPatriarch drawing = sheet.createDrawingPatriarch();
            ClientAnchor my_anchor = new HSSFClientAnchor();
            my_anchor.setCol1(4);
            my_anchor.setRow1(1);
            HSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
            my_picture.resize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream out = new FileOutputStream(new File(filename));){
            workbook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
