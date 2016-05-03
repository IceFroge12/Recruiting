package ua.kpi.nc.report.renderer;

import ua.kpi.nc.report.poi.RenderXLS;
import ua.kpi.nc.report.poi.RenderXLSX;

public class RendererFactory {

	public static ReportRenderer getXLSRenderer() {
		return new RenderXLS();
	}

	public static ReportRenderer getXLSXRenderer() {
		return new RenderXLSX();
	}

	public static ReportRenderer getJSONRenderer() {
		return new JSONRenderer();
	}
}
