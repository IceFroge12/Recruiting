package ua.kpi.nc.persistence.model.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.impl.real.RecruitmentImpl;
import ua.kpi.nc.service.RecruitmentService;

import java.sql.Timestamp;
/**
 @author Vova Korzh
 **/
public class RecruitmentProxy implements Recruitment {

    private Long id;
    private RecruitmentImpl recruitment;

    private RecruitmentService recruitmentService;

    public RecruitmentProxy() {

    }

    public RecruitmentProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return  id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;

    }

    @Override
    public String getName() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getName();
    }

    @Override
    public void setName(String name) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setName(name);
    }

    @Override
    public Timestamp getStartDate() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getStartDate();
    }

    @Override
    public void setStartDate(Timestamp startDate) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setStartDate(startDate);
    }

    @Override
    public Timestamp getEndDate() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getEndDate();
    }

    @Override
    public void setEndDate(Timestamp endDate) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setEndDate(endDate);
    }

    @Override
    public int getMaxGeneralGroup() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getMaxGeneralGroup();
    }

    @Override
    public void setMaxGeneralGroup(int maxGeneralGroup) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setMaxGeneralGroup(maxGeneralGroup);
    }

    @Override
    public int getMaxAdvancedGroup() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getMaxAdvancedGroup();
    }

    @Override
    public void setMaxAdvancedGroup(int maxAdvancedGroup) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setMaxAdvancedGroup(maxAdvancedGroup);
    }

    @Override
    public Timestamp getRegistrationDeadline() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getRegistrationDeadline();
    }

    @Override
    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setRegistrationDeadline(registrationDeadline);

    }

    @Override
    public Timestamp getScheduleChoicesDeadline() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getScheduleChoicesDeadline();
    }

    @Override
    public void setScheduleChoicesDeadline(Timestamp scheduleChoicesDeadline) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setScheduleChoicesDeadline(scheduleChoicesDeadline);
    }

    @Override
    public int getStudentsOnInterview() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getStudentsOnInterview();
    }

    @Override
    public void setStudentsOnInterview(int studentsOnInterview) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setStudentsOnInterview(studentsOnInterview);
    }

    @Override
    public int getTimeInterviewTech() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getTimeInterviewTech();
    }

    @Override
    public void setTimeInterviewTech(int timeInterviewTech) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setTimeInterviewTech(timeInterviewTech);
    }

    @Override
    public int getTimeInterviewSoft() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getTimeInterviewSoft();
    }

    @Override
    public void setTimeInterviewSoft(int timeInterviewSoft) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setTimeInterviewSoft(timeInterviewSoft);
    }

    @Override
    public int getNumberTechInterviewers() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getNumberTechInterviewers();
    }

    @Override
    public void setNumberTechInterviewers(int numberTechInterviewers) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setNumberTechInterviewers(numberTechInterviewers);
    }

    @Override
    public int getNumberSoftInterviewers() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getNumberSoftInterviewers();
    }

    @Override
    public void setNumberSoftInterviewers(int numberSoftInterviewers) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setNumberSoftInterviewers(numberSoftInterviewers);
    }

    @Override
    public int getNumberOfDays() {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        return recruitment.getNumberOfDays();
    }

    @Override
    public void setNumberOfDays(int numberOfDays) {
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
        recruitment.setNumberOfDays(numberOfDays);
    }

    private RecruitmentImpl downloadRecruitment() {
        return (RecruitmentImpl) recruitmentService.getRecruitmentById(id);
    }
}
