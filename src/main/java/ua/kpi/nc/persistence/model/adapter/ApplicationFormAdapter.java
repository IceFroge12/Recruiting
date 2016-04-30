package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;


/**
 * Created by Chalienko on 24.04.2016.
 */
public class ApplicationFormAdapter implements JsonSerializer<ApplicationForm> {

    @Override
    public JsonElement serialize(ApplicationForm applicationForm, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ID", applicationForm.getId());
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
        jsonObject.add("user", jsonUser);
        JsonArray jsonQuestions = new JsonArray();
        for (FormQuestion question : applicationForm.getQuestions()) {
            JsonObject jsonQuestion = new JsonObject();
            jsonQuestion.addProperty("questionTitle", question.getTitle());
            jsonQuestion.addProperty("questionType", question.getQuestionType().getTypeTitle());
            JsonArray jsonAnswerVariants = new JsonArray();
            for (FormAnswerVariant variant : question.getFormAnswerVariants()) {
                JsonObject jsonAnswerVariant = new JsonObject();
                jsonAnswerVariant.addProperty("variant", variant.getAnswer());
                jsonAnswerVariants.add(jsonAnswerVariant);
            }
            jsonQuestion.add("variants", jsonAnswerVariants);
            JsonArray jsonAnswers = new JsonArray();
            for (FormAnswer answer : question.getAnswers()) {
                for (FormAnswer answerApplication : applicationForm.getAnswers())
                    if (Objects.equals(answerApplication.getId(), answer.getId())) {
                        JsonObject jsonAnswer = new JsonObject();
                        if (answer.getAnswer() != null) {
                            jsonAnswer.addProperty("answer", answer.getAnswer());
                        } else {
                            jsonAnswer.addProperty("answer", answer.getFormAnswerVariant().getAnswer());
                        }
                        jsonAnswers.add(jsonAnswer);
                    }
            }
            jsonQuestion.add("answers", jsonAnswers);
            jsonQuestions.add(jsonQuestion);
        }
        jsonObject.add("questions", jsonQuestions);
        return jsonObject;
    }


}