package ua.kpi.nc.report.renderer;

public class RendererFactory {

	public static ReportRenderer getXLSRenderer() {
		return null;
	}

	public static ReportRenderer getXLSXRenderer() {
		return null;
	}

	public static ReportRenderer getJSONRenderer() {
		return new JSONRender();
	}
}
