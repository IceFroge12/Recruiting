package ua.kpi.nc.report.renderer;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ua.kpi.nc.report.Report;

public class JSONRenderer implements ReportRenderer {

	private static Logger log = LoggerFactory.getLogger(JSONRenderer.class);

	@Override
	public void render(Report report, HttpServletResponse response) {
		Gson gson = new Gson();
		try {
			response.getWriter().print(gson.toJson(report));
		} catch (IOException e) {
			if(log.isErrorEnabled()) {
				log.error("Cannot render report.");
			}
		}
	}

}
