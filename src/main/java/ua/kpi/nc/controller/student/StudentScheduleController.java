package ua.kpi.nc.controller.student;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ua.kpi.nc.persistence.dto.StudentSchedulePriorities;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.service.ScheduleTimePointService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.TimePriorityTypeService;
import ua.kpi.nc.service.UserTimePriorityService;

/**
 * Created by dima on 26.04.16.
 */
@RequestMapping("/student")
@Controller
public class StudentScheduleController {

	private static Logger log = LoggerFactory.getLogger(StudentScheduleController.class);

	@RequestMapping(value = "schedule", method = RequestMethod.GET)
	@ResponseBody
	public String getStudentSchedule() {
		User user = new UserProxy(33L);
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();
		List<ScheduleTimePoint> finalTimePoints = scheduleTimePointService.getFinalTimePointByUserId(user.getId());
		if (finalTimePoints.size() > 0) {
			jsonObject.addProperty("finalTimePoint", dataFormat.format(finalTimePoints.get(0).getTimePoint()));
		} else {
			UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
			TimePriorityTypeService timePriorityTypeService = ServiceFactory.getTimePriorityTypeService();
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
			for (ScheduleTimePoint timePoint : timePoints) {
				JsonObject jsonTimePoint = new JsonObject();
				jsonTimePoint.addProperty("timePoint", dataFormat.format(timePoint.getTimePoint()));
				UserTimePriority userTimePriority = userTimePriorityService.getByUserTime(user, timePoint);
				jsonTimePoint.addProperty("priority", userTimePriority.getTimePriorityType().getPriority());
				jsonTimePoints.add(jsonTimePoint);
			}
			jsonObject.add("timePoints", jsonTimePoints);
		}
		return gson.toJson(jsonObject);
	}

	@RequestMapping(value = "updateSchedule", method = RequestMethod.POST)
	@ResponseBody
	public void updateStudentSchedule(@RequestBody StudentSchedulePriorities[] dtoPriorities) {
		try {
			User user = new UserProxy(33L);
			UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
			ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();
			TimePriorityTypeService timePriorityTypeService = ServiceFactory.getTimePriorityTypeService();
			for (StudentSchedulePriorities priorityDto : dtoPriorities) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				Date parsedDate;
				parsedDate = dateFormat.parse(priorityDto.getTimePoint());
				Timestamp timestamp = new Timestamp(parsedDate.getTime());
				ScheduleTimePoint timePoint = scheduleTimePointService.getScheduleTimePointByTimepoint(timestamp);
				UserTimePriority userTimePriority = userTimePriorityService.getByUserTime(user, timePoint);
				TimePriorityType priorityType = timePriorityTypeService.getByPriority(priorityDto.getPriority());
				userTimePriority.setTimePriorityType(priorityType);
				userTimePriorityService.updateUserPriority(userTimePriority);
			}
		} catch (ParseException e) {
			if (log.isErrorEnabled()) {
				log.error("Cannot parse timepoint.");
			}
		}

	}
}
