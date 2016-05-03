package ua.kpi.nc.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
@Controller
@RequestMapping("/admin")
public class AdminReportsController {

	@RequestMapping(value = "reports", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView modelAndView = new ModelAndView("adminreports");
		return modelAndView;
	}

	@RequestMapping(value = "reports/approved.{format}", method = RequestMethod.GET)
	@ResponseBody
	public void generateReportOfApproved(@PathVariable String format, HttpServletResponse response) throws IOException {
		ReportService service = ServiceFactory.getReportService();
		Report report = service.getReportOfApproved();
		renderReport(report, format, response);
	}

	@RequestMapping(value = "reports/answers.{format}/{questionId}", method = RequestMethod.GET)
	@ResponseBody
	public void generateReportOfAnswers(@PathVariable String format, @PathVariable Long questionId,
			HttpServletResponse response) throws IOException {
		ReportService service = ServiceFactory.getReportService();
		Report report = service.getReportOfAnswers(new FormQuestionProxy(questionId));
		renderReport(report, format, response);
	}

	private void renderReport(Report report, String format, HttpServletResponse response) throws IOException {
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
		case "xlsx":
			renderer = RendererFactory.getXLSXRenderer();
			mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + report.getTitle() + ".xlsx\""));
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
