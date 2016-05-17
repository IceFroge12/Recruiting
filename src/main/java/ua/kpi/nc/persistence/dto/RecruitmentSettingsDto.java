package ua.kpi.nc.persistence.dto;

/**
 * Created by Admin on 06.05.2016.
 */
public class RecruitmentSettingsDto {

    private Long id;

    private String name;

    private String registrationDeadline;

    private String scheduleChoicesDeadline;

    private String endDate;

    private int maxGeneralGroup;

    private int maxAdvancedGroup;

    public RecruitmentSettingsDto() {
    }


    public RecruitmentSettingsDto(Long id, String name, String registrationDeadline, String scheduleChoicesDeadline, String endDate, int maxGeneralGroup, int maxAdvancedGroup) {
        this.id = id;
        this.name = name;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
        this.endDate = endDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getScheduleChoicesDeadline() {
        return scheduleChoicesDeadline;
    }

    public void setScheduleChoicesDeadline(String scheduleChoicesDeadline) {
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    public int getMaxGeneralGroup() {
        return maxGeneralGroup;
    }

    public void setMaxGeneralGroup(int maxGeneralGroup) {
        this.maxGeneralGroup = maxGeneralGroup;
    }

    public int getMaxAdvancedGroup() {
        return maxAdvancedGroup;
    }

    public void setMaxAdvancedGroup(int maxAdvancedGroup) {
        this.maxAdvancedGroup = maxAdvancedGroup;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RecruitmentSettingsDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationDeadline='" + registrationDeadline + '\'' +
                ", scheduleChoicesDeadline='" + scheduleChoicesDeadline + '\'' +
                ", endDate='" + endDate + '\'' +
                ", maxGeneralGroup=" + maxGeneralGroup +
                ", maxAdvancedGroup=" + maxAdvancedGroup +
                '}';
    }
}
