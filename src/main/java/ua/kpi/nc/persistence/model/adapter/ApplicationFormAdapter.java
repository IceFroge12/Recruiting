package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.*;
import ua.kpi.nc.persistence.model.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Chalienko on 24.04.2016.
 */
public class ApplicationFormAdapter extends AbstractQuestionsWithAnswersAdapter implements JsonSerializer<ApplicationForm> {

	@Override
	public JsonElement serialize(ApplicationForm applicationForm, Type type,
			JsonSerializationContext jsonSerializationContext) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", applicationForm.getId());
		jsonObject.addProperty("status", applicationForm.getStatus().getTitle());
		jsonObject.addProperty("active", applicationForm.isActive());
		JsonObject jsonRecruitment = new JsonObject();
		Recruitment recruitment = applicationForm.getRecruitment();
		jsonRecruitment.addProperty("name", recruitment.getName());
		jsonObject.add("recruitment", jsonRecruitment);
		jsonObject.addProperty("photoScope", applicationForm.getPhotoScope());
		User user = applicationForm.getUser();
		JsonObject jsonUser = new JsonObject();
		jsonUser.addProperty("firstName", user.getFirstName());
		jsonUser.addProperty("lastName", user.getLastName());
		jsonUser.addProperty("email", user.getEmail());
		jsonUser.addProperty("secondName", user.getSecondName());
		jsonObject.add("user", jsonUser);
		JsonArray jsonQuestions = new JsonArray();

		Map<FormQuestion, JsonObject> questionsMap = generateQuestionsAndAnswers(applicationForm.getAnswers());

		for (FormQuestion question : questionsMap.keySet()) {
			jsonQuestions.add(questionsMap.get(question));
		}
		jsonObject.add("questions", jsonQuestions);
		return jsonObject;
	}
}