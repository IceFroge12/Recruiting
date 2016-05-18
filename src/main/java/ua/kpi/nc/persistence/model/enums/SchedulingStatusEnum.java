package ua.kpi.nc.persistence.model.enums;

/**
 * @author Korzh
 */
public enum SchedulingStatusEnum {

    DATES(1L), TIME_POINTS(2L), STAFF_SCHEDULING(3L), STUDENT_SCHEDULING(4L);
    Long id;

    public static String valueOf(SchedulingStatusEnum schedulingStatusEnum){
        switch (schedulingStatusEnum) {
            case DATES:
                return "dates";
            case TIME_POINTS:
                return "time_points";
            case STAFF_SCHEDULING:
                return "staff_scheduling";
            case STUDENT_SCHEDULING:
                return "student_scheduling";
        }
        throw new IllegalArgumentException("No status defined for");
    }

    SchedulingStatusEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
