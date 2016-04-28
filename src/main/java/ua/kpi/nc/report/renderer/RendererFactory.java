package ua.kpi.nc.report.renderer;

import ua.kpi.nc.report.poi.RenderXLS;
import ua.kpi.nc.report.poi.RenderXLSX;

public class RendererFactory {

	public static ReportRenderer getXLSRenderer() {
		return new RenderXLS("121.xls");
	}

	public static ReportRenderer getXLSXRenderer() {
		return new RenderXLSX("121.xlsx");
	}

	public static ReportRenderer getJSONRenderer() {
		return new JSONRenderer();
	}
}
