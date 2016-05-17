package ua.kpi.nc.persistence.dto;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;

/**
 * Created by Alona on 17.05.2016.
 */
public class ScheduleOverallDto {
    private String timePoint;
    private long amountOfStudents;
    private long amountOfTech;
    private long amountOfSoft;

    public ScheduleOverallDto() {
    }

    public ScheduleOverallDto(String timePoint) {
        this.timePoint = timePoint;
    }

    public ScheduleOverallDto(String timePoint, long amountOfStudents, long amountOfTech, long amountOfSoft) {
        this.timePoint = timePoint;
        this.amountOfStudents = amountOfStudents;
        this.amountOfTech = amountOfTech;
        this.amountOfSoft = amountOfSoft;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public long getAmountOfStudents() {
        return amountOfStudents;
    }

    public void setAmountOfStudents(long amountOfStudents) {
        this.amountOfStudents = amountOfStudents;
    }

    public long getAmountOfTech() {
        return amountOfTech;
    }

    public void setAmountOfTech(long amountOfTech) {
        this.amountOfTech = amountOfTech;
    }

    public long getAmountOfSoft() {
        return amountOfSoft;
    }

    public void setAmountOfSoft(long amountOfSoft) {
        this.amountOfSoft = amountOfSoft;
    }
}
