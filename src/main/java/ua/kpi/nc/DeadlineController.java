package ua.kpi.nc;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by IO on 11.05.2016.
 */
public class DeadlineController {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    public void setDeadLine(Timestamp date){
        scheduledExecutorService.schedule(() -> {
            action();
            scheduledExecutorService.shutdown();
        }, calculateDate(date), TimeUnit.MILLISECONDS);
    }

    private void action(){
        applicationFormService.changeCurrentsAppFormStatus(StatusEnum.REGISTERED.getId(), StatusEnum.IN_REVIEW.getId());
    }

    private Long calculateDate(Timestamp date){
        return date.getTime() - System.currentTimeMillis();
    }
}
