package ua.kpi.nc.persistence.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Role;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Vova on 21.04.2016.
 */
public class RecruitmentImpl {


    private Long id;

    private String name;

    private Timestamp startDate;

    private Timestamp endDate;

    private int maxGeneralGroup;

    private int maxAdvancedGroup;

    private Timestamp registrationDeadline;

    private Timestamp scheduleChoicesDeadline;

    private int studentsOnInterview;

    private int timeInterviewTech;

    private int timeInterviewSoft;

    private int numberTechInterviwers;

    private int numberSoftInterviwers;

    private int numberOfDays;

    public RecruitmentImpl() {
    }

    public RecruitmentImpl(int numberOfDays, Long id, String name, Timestamp startDate, Timestamp endDate,
                           int maxGeneralGroup, int maxAdvancedGroup, Timestamp registrationDeadline, Timestamp
                                   scheduleChoicesDeadline, int studentsOnInterview, int timeInterviewTech,
                           int timeInterviewSoft, int numberTechInterviwers, int numberSoftInterviwers) {
        this.numberOfDays = numberOfDays;
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
        this.studentsOnInterview = studentsOnInterview;
        this.timeInterviewTech = timeInterviewTech;
        this.timeInterviewSoft = timeInterviewSoft;
        this.numberTechInterviwers = numberTechInterviwers;
        this.numberSoftInterviwers = numberSoftInterviwers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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

    public Timestamp getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Timestamp getScheduleChoicesDeadline() {
        return scheduleChoicesDeadline;
    }

    public void setScheduleChoicesDeadline(Timestamp scheduleChoicesDeadline) {
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    public int getStudentsOnInterview() {
        return studentsOnInterview;
    }

    public void setStudentsOnInterview(int studentsOnInterview) {
        this.studentsOnInterview = studentsOnInterview;
    }

    public int getTimeInterviewTech() {
        return timeInterviewTech;
    }

    public void setTimeInterviewTech(int timeInterviewTech) {
        this.timeInterviewTech = timeInterviewTech;
    }

    public int getTimeInterviewSoft() {
        return timeInterviewSoft;
    }

    public void setTimeInterviewSoft(int timeInterviewSoft) {
        this.timeInterviewSoft = timeInterviewSoft;
    }

    public int getNumberTechInterviwers() {
        return numberTechInterviwers;
    }

    public void setNumberTechInterviwers(int numberTechInterviwers) {
        this.numberTechInterviwers = numberTechInterviwers;
    }

    public int getNumberSoftInterviwers() {
        return numberSoftInterviwers;
    }

    public void setNumberSoftInterviwers(int numberSoftInterviwers) {
        this.numberSoftInterviwers = numberSoftInterviwers;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RecruitmentImpl that = (RecruitmentImpl) o;

        return new EqualsBuilder()
                .append(maxGeneralGroup, that.maxGeneralGroup)
                .append(maxAdvancedGroup, that.maxAdvancedGroup)
                .append(studentsOnInterview, that.studentsOnInterview)
                .append(timeInterviewTech, that.timeInterviewTech)
                .append(timeInterviewSoft, that.timeInterviewSoft)
                .append(numberTechInterviwers, that.numberTechInterviwers)
                .append(numberSoftInterviwers, that.numberSoftInterviwers)
                .append(numberOfDays, that.numberOfDays)
                .append(id, that.id)
                .append(name, that.name)
                .append(startDate, that.startDate)
                .append(endDate, that.endDate)
                .append(registrationDeadline, that.registrationDeadline)
                .append(scheduleChoicesDeadline, that.scheduleChoicesDeadline)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(startDate)
                .append(endDate)
                .append(maxGeneralGroup)
                .append(maxAdvancedGroup)
                .append(registrationDeadline)
                .append(scheduleChoicesDeadline)
                .append(studentsOnInterview)
                .append(timeInterviewTech)
                .append(timeInterviewSoft)
                .append(numberTechInterviwers)
                .append(numberSoftInterviwers)
                .append(numberOfDays)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "RecruitmentImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxGeneralGroup=" + maxGeneralGroup +
                ", maxAdvancedGroup=" + maxAdvancedGroup +
                ", registrationDeadline=" + registrationDeadline +
                ", scheduleChoicesDeadline=" + scheduleChoicesDeadline +
                ", studentsOnInterview=" + studentsOnInterview +
                ", timeInterviewTech=" + timeInterviewTech +
                ", timeInterviewSoft=" + timeInterviewSoft +
                ", numberTechInterviwers=" + numberTechInterviwers +
                ", numberSoftInterviwers=" + numberSoftInterviwers +
                ", numberOfDays=" + numberOfDays +
                '}';
    }
}
