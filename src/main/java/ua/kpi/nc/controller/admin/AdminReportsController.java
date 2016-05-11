package ua.kpi.nc.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.impl.proxy.FormQuestionProxy;
import ua.kpi.nc.report.Report;
import ua.kpi.nc.report.renderer.RendererFactory;
import ua.kpi.nc.report.renderer.ReportRenderer;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.ReportService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminReportsController {

//	@RequestMapping(value = "reports", method = RequestMethod.GET)
//	public ModelAndView adminPage() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		ModelAndView modelAndView = new ModelAndView("adminreports");
//		return modelAndView;
//	}

	@RequestMapping(value = "reports/approved.{format}", method = RequestMethod.GET)
	@ResponseBody
	public void generateReportOfApproved(@PathVariable String format, HttpServletResponse response) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ReportService service = ServiceFactory.getReportService();
		Report report = service.getReportOfApproved();
		renderReport(report, format, response);
	}

	@RequestMapping(value = "reports/answers.{format}/{questionId}", method = RequestMethod.GET)
	@ResponseBody
	public void generateReportOfAnswers(@PathVariable String format, @PathVariable Long questionId,
			HttpServletResponse response) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ReportService service = ServiceFactory.getReportService();
		Report report = service.getReportOfAnswers(new FormQuestionProxy(questionId));
		renderReport(report, format, response);
	}

	private void renderReport(Report report, String format, HttpServletResponse response) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ReportRenderer renderer = null;
		String mimeType = null;
		switch (format) {
		case "json":
			renderer = RendererFactory.getJSONRenderer();
			mimeType = "application/json";
			break;
		case "xls":
			renderer = RendererFactory.getXLSRenderer();
			mimeType = "application/vnd.ms-excel";
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + report.getTitle() + ".xls\""));
			break;
		case "xlsx":
			renderer = RendererFactory.getXLSXRenderer();
			mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + report.getTitle() + ".xlsx\""));
			break;
		}
		if (mimeType != null) {
			response.setContentType(mimeType);
		}
		if (renderer != null) {
			renderer.render(report, response.getOutputStream());
		}
	}

	@RequestMapping(value = "reports/answers/questions")
	@ResponseBody
	public String getQuestions() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		FormQuestionService questionService = ServiceFactory.getFormQuestionService();
		List<FormQuestion> questions = questionService.getAll();
		JsonArray jsonArray = new JsonArray();
		for (FormQuestion formQuestion : questions) {
			if (formQuestion.getFormAnswerVariants() != null && formQuestion.isEnable()) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", formQuestion.getId());
				jsonObject.addProperty("title", formQuestion.getTitle());
				jsonArray.add(jsonObject);
			}
		}
		Gson gson = new Gson();
		return gson.toJson(jsonArray);
	}

}
