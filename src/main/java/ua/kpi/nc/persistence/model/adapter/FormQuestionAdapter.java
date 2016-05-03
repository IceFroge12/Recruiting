package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormQuestion;

import java.lang.reflect.Type;

/**
 * Created by Chalienko on 03.05.2016.
 */
public class FormQuestionAdapter implements JsonSerializer<FormQuestion> {
    @Override
    public JsonElement serialize(FormQuestion formQuestion, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", formQuestion.getId());
        jsonObject.addProperty("title", formQuestion.getTitle());
        jsonObject.addProperty("type", formQuestion.getQuestionType().getTypeTitle());
        jsonObject.addProperty("enable", formQuestion.isEnable());
        return jsonObject;
    }

}
