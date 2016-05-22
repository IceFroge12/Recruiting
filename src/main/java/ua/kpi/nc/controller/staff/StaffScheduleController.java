package ua.kpi.nc.controller.staff;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.dto.UserTimePriorityDto;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.proxy.ScheduleTimePointProxy;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.UserTimePriorityService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Admin on 16.05.2016.
 */
@RestController
@RequestMapping("/staff")
public class StaffScheduleController {

    private UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
    private UserService userService = ServiceFactory.getUserService();

    @RequestMapping(value = "getUserTimePriorities", method = RequestMethod.POST)
    public List<UserTimePriorityDto> getUserTimePriorities() {
        return userTimePriorityService.getAllTimePriorityForUserById(getCurrentUser().getId());
    }

    @RequestMapping(value = "saveVariants", method = RequestMethod.POST)
    public void saveAppropriateVariant(@RequestBody List<UserTimePriorityDto> userTimePriorityListDto) {
        List<UserTimePriority> userTimePriorities = userTimePriorityService.getAllUserTimePriorities(getCurrentUser().getId());
        List<UserTimePriority> updatingUserTimePriorities = IntStream.range(0, userTimePriorityListDto.size())
                .mapToObj(i -> {
                    UserTimePriority userTimePriority = new UserTimePriority();
                    userTimePriority.setUser(userTimePriorities.get(i).getUser());
                    userTimePriority.setScheduleTimePoint(new ScheduleTimePointProxy(userTimePriorityListDto.get(i).getTimeStampId()));
                    userTimePriority.setTimePriorityType(new TimePriorityType(userTimePriorityListDto.get(i).getIdPriorityType()));
                    return userTimePriority;
                }).collect(Collectors.toList());

        userTimePriorityService.batchUpdateUserPriority(updatingUserTimePriorities);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.getUserByUsername(name);
    }

}
