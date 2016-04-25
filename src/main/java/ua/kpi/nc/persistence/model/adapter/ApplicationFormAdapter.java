package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.*;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;

import java.lang.reflect.Type;


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
        jsonRecruitment.addProperty("name",recruitment.getName());
        jsonObject.add("recruitment", jsonRecruitment);
        jsonObject.addProperty("photoScope", applicationForm.getPhotoScope());
        User user = applicationForm.getUser();
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty("firstName",user.getFirstName());
        jsonUser.addProperty("lastName", user.getLastName());
        jsonUser.addProperty("email", user.getEmail());
        jsonObject.add("user", jsonUser);
        JsonArray jsonAnswers = new JsonArray();
        for(FormAnswer answer : applicationForm.getAnswers()){
            JsonObject jsonAnswer= new JsonObject();
            jsonAnswer.addProperty("question", answer.getFormQuestion().getTitle());
            if (answer.getAnswer() != null){
                jsonAnswer.addProperty("answer", answer.getAnswer());
            }else {
                jsonAnswer.addProperty("answer", answer.getFormAnswerVariant().getAnswer());
            }
            jsonAnswers.add(jsonAnswer);
        }
        jsonObject.add("answers", jsonAnswers);
        return jsonObject;
    }
}