package ua.kpi.nc.reports;

import java.util.ArrayList;
import java.util.List;

public class Report {

	private String title;

	private Row header;

	private List<Row> rows;

	public Report() {
		rows = new ArrayList<>();
	}

	public Report(String title) {
		this();
		this.title = title;
	}

	public Row getHeader() {
		return header;
	}

	public void setHeader(Row header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public boolean addRow(Row e) {
		return rows.add(e);
	}

}
