package ua.kpi.nc.controller.student;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.StudentSchedulePriorityDto;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
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
@Controller
public class StudentScheduleController {

	private Gson gson = new Gson();
	private static Logger log = LoggerFactory.getLogger(StudentScheduleController.class);
	private UserService userService = ServiceFactory.getUserService();
	private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
	private ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();
	private UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
	private TimePriorityTypeService timePriorityTypeService = ServiceFactory.getTimePriorityTypeService();

	private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm";

	@RequestMapping(value = "schedule", method = RequestMethod.GET)
	@ResponseBody
	public String getStudentSchedule() {
		User student = getAuthorizedUser();
		Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
		if (recruitment == null) {
			return gson.toJson(new MessageDto("There is no recruitment now.", MessageDtoType.INFO));
		}
		if (recruitment.getScheduleChoicesDeadline() == null) {
			return gson.toJson(new MessageDto("You cannot choice schedule now.", MessageDtoType.INFO));
		}
		if (isDeadlineEnded(recruitment.getScheduleChoicesDeadline())) {
			return gson.toJson(finalTimePointToJson(student));
		} else {
			return gson.toJson(userTimePrioritiesToJson(student));
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
	@ResponseBody
	public String updateStudentSchedule(@RequestBody StudentSchedulePriorityDto[] dtoPriorities) {
		try {
			User user = getAuthorizedUser();
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
			return gson.toJson(new MessageDto("Your priorities were updated.", MessageDtoType.SUCCESS));
		} catch (ParseException e) {
			if (log.isErrorEnabled()) {
				log.error("Cannot parse timepoint.");
			}
			return gson.toJson(new MessageDto("Your priorities weren't updated.", MessageDtoType.ERROR));
		}

	}

	private boolean isDeadlineEnded(Timestamp deadline) {
		return new Date().after(deadline);
	}

	private User getAuthorizedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return userService.getUserByUsername(name);
	}
}
