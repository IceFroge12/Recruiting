package ua.kpi.nc.report.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by Алексей on 28.04.2016.
 */
public class RenderXLS extends AbsRender {

	public RenderXLS(String filename) {
		this.filename = filename;
		wb = new HSSFWorkbook();
	}

}
