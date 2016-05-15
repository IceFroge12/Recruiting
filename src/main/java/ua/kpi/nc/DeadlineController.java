package ua.kpi.nc;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by IO on 11.05.2016.
 */
public class DeadlineController {

    private static class DeadlineControllerHolder{
        static DeadlineController HOLDER_INSTANCE = new DeadlineController();
    }

    private static final ScheduledExecutorService registeredStatusDeadline = Executors.newScheduledThreadPool(1);
    private static final ScheduledExecutorService endOfRecruitingDeadLine = Executors.newScheduledThreadPool(1);
    private static final ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private static final RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

    private DeadlineController() {
        Recruitment recruitment = recruitmentService.getLastRecruitment();
        if (null != recruitment){
            checkEndOfRecruitmentDeadLine(recruitment);
            checkRegisteredDeadLine(recruitment);
        }
    }

    public static DeadlineController getInstance(){
        return DeadlineControllerHolder.HOLDER_INSTANCE;
    }

    public void setRegisteredDeadline(Timestamp date){
        registeredStatusDeadline.schedule(() -> {
            actionForRegisteredDeadline();
            registeredStatusDeadline.shutdown();
        }, calculateDate(date), TimeUnit.MILLISECONDS);
    }

    public void setEndOfRecruitingDeadLine(Timestamp date){
        endOfRecruitingDeadLine.schedule(() -> {
            actionForEndOfRecruitingDeadLne();
            registeredStatusDeadline.shutdown();
        }, calculateDate(date), TimeUnit.MILLISECONDS);
    }

    private void checkEndOfRecruitmentDeadLine(Recruitment recruitment){
        if (recruitment.getEndDate().getTime() >= System.currentTimeMillis()){
            setEndOfRecruitingDeadLine(recruitment.getEndDate());
        }else {
            actionForEndOfRecruitingDeadLne(recruitment);
        }
    }

    private void checkRegisteredDeadLine(Recruitment recruitment){
        if (recruitment.getRegistrationDeadline().getTime() >= System.currentTimeMillis()){
            setRegisteredDeadline(recruitment.getRegistrationDeadline());
        }else {
            actionForRegisteredDeadline();
        }
    }

    private void actionForRegisteredDeadline(){
        applicationFormService.changeCurrentsAppFormStatus(StatusEnum.REGISTERED.getId(), StatusEnum.IN_REVIEW.getId());
    }

    private void actionForEndOfRecruitingDeadLne(Recruitment recruitment){
        for (ApplicationForm applicationForm : applicationFormService.getByRecruitment(recruitment)) {
            applicationForm.setActive(false);
            //TODO don't delete
//            applicationFormService.updateApplicationForm(applicationForm);
            System.out.println(applicationForm.toString());
        }
    }

    private void actionForEndOfRecruitingDeadLne(){
        for (ApplicationForm applicationForm : applicationFormService.getCurrentApplicationForms()) {
            applicationForm.setActive(false);
            //TODO don't delete
//            applicationFormService.updateApplicationForm(applicationForm);
            System.out.println(applicationForm.toString());
        }
    }

    private Long calculateDate(Timestamp date){
        return date.getTime() - System.currentTimeMillis();
    }
}
