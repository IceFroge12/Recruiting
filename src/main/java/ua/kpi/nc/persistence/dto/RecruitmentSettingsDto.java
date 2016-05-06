package ua.kpi.nc.persistence.dto;

/**
 * Created by Admin on 06.05.2016.
 */
public class RecruitmentSettingsDto {

    private String name;

    private String registrationDeadline;

    private String scheduleChoicesDeadline;

    private int maxGeneralGroup;

    private int maxAdvancedGroup;

    public RecruitmentSettingsDto() {
    }

    public RecruitmentSettingsDto(String name, String registrationDeadline, String scheduleChoicesDeadline,
                                  int maxGeneralGroup, int maxAdvancedGroup) {
        this.name = name;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
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

    @Override
    public String toString() {
        return "RecruitmentSettingsDto{" +
                "maxAdvancedGroup=" + maxAdvancedGroup +
                ", maxGeneralGroup=" + maxGeneralGroup +
                ", scheduleChoicesDeadline=" + scheduleChoicesDeadline +
                ", registrationDeadline=" + registrationDeadline +
                '}';
    }
}
