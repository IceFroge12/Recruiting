package ua.kpi.nc.persistence.dto;

public class StudentSchedulePriorities {

	private String timePoint;

	private String priority;

	public StudentSchedulePriorities() {
	}

	public StudentSchedulePriorities(String timePoint, String priority) {
		this.timePoint = timePoint;
		this.priority = priority;
	}

	public String getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
