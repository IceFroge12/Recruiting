package ua.kpi.nc.controller.student;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.StudentSchedulePriorityDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ScheduleTimePointService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.TimePriorityTypeService;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.UserTimePriorityService;

/**
 * Created by dima on 26.04.16.
 */
@RequestMapping("/student")
@RestController
public class StudentScheduleController {

	private static Logger log = LoggerFactory.getLogger(StudentScheduleController.class);
	private UserService userService = ServiceFactory.getUserService();
	private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
	private ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();
	private UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
	private TimePriorityTypeService timePriorityTypeService = ServiceFactory.getTimePriorityTypeService();
	private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

	private static Gson gson = new Gson();
	private static final String NOT_INVITED_MESSAGE = gson
			.toJson(new MessageDto("You weren't invited to interview.", MessageDtoType.INFO));
	private static final String NO_RECRUITMENT_MESSAGE = gson
			.toJson(new MessageDto("There is no recruitment now.", MessageDtoType.INFO));
	private static final String NO_USER_PRIOTIES_MESSAGE = gson
			.toJson(new MessageDto("You cannot choice schedule now.", MessageDtoType.INFO));
	private static final String PRIOTIES_UPDATED_MESSAGE = gson
			.toJson(new MessageDto("Your priorities were updated.", MessageDtoType.SUCCESS));
	private static final String PRIOTIES_NOT_UPDATED_MESSAGE = gson
			.toJson(new MessageDto("Your priorities weren't updated.", MessageDtoType.ERROR));
	private static final String SCHEDULE_CHOICES_DEADLINE_MESSAGE = gson.toJson(
			new MessageDto("You cannot update priorities after schedule choices deadline.", MessageDtoType.ERROR));

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

	@RequestMapping(value = "schedule", method = RequestMethod.GET)
	public String getStudentSchedule() {
		User student = userService.getAuthorizedUser();
		Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
		if (recruitment == null) {
			return NO_RECRUITMENT_MESSAGE;
		}
		if (userTimePriorityService.isSchedulePrioritiesExistStudent()) {
			ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
			if (applicationForm == null) {
				return NOT_INVITED_MESSAGE;
			}
			if (scheduleTimePointService.isScheduleExists()) {
				if (applicationForm.getStatus().getId().equals(StatusEnum.REJECTED.getId())) {
					return NOT_INVITED_MESSAGE;
				}
				return gson.toJson(finalTimePointToJson(student));
			}
			if (applicationForm.getStatus().getId().equals(StatusEnum.APPROVED.getId())) {
				return gson.toJson(userTimePrioritiesToJson(student));
			}
			return NOT_INVITED_MESSAGE;
		} else {
			return NO_USER_PRIOTIES_MESSAGE;
		}
	}

	private JsonObject finalTimePointToJson(User student) {
		JsonObject jsonObject = new JsonObject();
		List<ScheduleTimePoint> finalTimePoints = scheduleTimePointService.getFinalTimePointByUserId(student.getId());
		SimpleDateFormat dataFormat = new SimpleDateFormat(DATE_FORMAT);
		jsonObject.addProperty("finalTimePoint", dataFormat.format(finalTimePoints.get(0).getTimePoint()));
		return jsonObject;
	}

	private JsonObject userTimePrioritiesToJson(User student) {
		JsonObject jsonObject = new JsonObject();
		List<TimePriorityType> timeTypes = timePriorityTypeService.getAll();
		List<ScheduleTimePoint> timePoints = scheduleTimePointService.getAll();
		JsonArray jsonPriorityTypes = new JsonArray();
		for (TimePriorityType timePriorityType : timeTypes) {
			JsonObject jsonPriorityType = new JsonObject();
			jsonPriorityType.addProperty("type", timePriorityType.getPriority());
			jsonPriorityTypes.add(jsonPriorityType);
		}
		jsonObject.add("priorityTypes", jsonPriorityTypes);
		JsonArray jsonTimePoints = new JsonArray();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		for (ScheduleTimePoint timePoint : timePoints) {
			JsonObject jsonTimePoint = new JsonObject();
			jsonTimePoint.addProperty("timePoint", dateFormat.format(timePoint.getTimePoint()));
			UserTimePriority userTimePriority = userTimePriorityService.getByUserTime(student, timePoint);
			jsonTimePoint.addProperty("priority", userTimePriority.getTimePriorityType().getPriority());
			jsonTimePoints.add(jsonTimePoint);
		}
		jsonObject.add("timePoints", jsonTimePoints);
		return jsonObject;
	}

	@RequestMapping(value = "updateSchedule", method = RequestMethod.POST)
	public String updateStudentSchedule(@RequestBody StudentSchedulePriorityDto[] dtoPriorities) {
		Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
		if (recruitment.getScheduleChoicesDeadline().before(new Date())) {
			return SCHEDULE_CHOICES_DEADLINE_MESSAGE;
		}
		try {
			User user = userService.getAuthorizedUser();
			for (StudentSchedulePriorityDto priorityDto : dtoPriorities) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
				Date parsedDate = dateFormat.parse(priorityDto.getTimePoint());
				Timestamp timestamp = new Timestamp(parsedDate.getTime());
				ScheduleTimePoint timePoint = scheduleTimePointService.getScheduleTimePointByTimepoint(timestamp);
				if (timePoint != null) {
					UserTimePriority userTimePriority = userTimePriorityService.getByUserTime(user, timePoint);
					TimePriorityType priorityType = timePriorityTypeService.getByPriority(priorityDto.getPriority());
					userTimePriority.setTimePriorityType(priorityType);
					userTimePriorityService.updateUserPriority(userTimePriority);
				}
			}
			return PRIOTIES_UPDATED_MESSAGE;
		} catch (ParseException e) {
			if (log.isErrorEnabled()) {
				log.error("Cannot parse timepoint.");
			}
			return PRIOTIES_NOT_UPDATED_MESSAGE;
		}

	}
}
