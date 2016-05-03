package ua.kpi.nc.report.renderer;

import java.io.OutputStream;
import java.io.PrintStream;

import com.google.gson.Gson;

import ua.kpi.nc.report.Report;

public class JSONRenderer implements ReportRenderer {

	@Override
	public void render(Report report, OutputStream out) {
		Gson gson = new Gson();
		PrintStream printStream = new PrintStream(out);
		printStream.print(gson.toJson(report));
		printStream.flush();
	}

}
