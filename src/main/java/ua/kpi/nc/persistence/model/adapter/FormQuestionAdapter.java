package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.*;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;

import java.lang.reflect.Type;

public class FormQuestionAdapter implements JsonSerializer<FormQuestion> {
    @Override
    public JsonElement serialize(FormQuestion formQuestion, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", formQuestion.getId());
        jsonObject.addProperty("title", formQuestion.getTitle());
        jsonObject.addProperty("type", formQuestion.getQuestionType().getTypeTitle());
        jsonObject.addProperty("mandatory", formQuestion.isMandatory());
        jsonObject.addProperty("enable", formQuestion.isEnable());
        jsonObject.addProperty("order", formQuestion.getOrder());
        JsonArray jsonAnswerVariants = new JsonArray();
        if (formQuestion.getFormAnswerVariants() != null) {
            for (FormAnswerVariant variant : formQuestion.getFormAnswerVariants()) {
                JsonObject jsonAnswerVariant = new JsonObject();
                jsonAnswerVariant.addProperty("variant", variant.getAnswer());
                jsonAnswerVariants.add(jsonAnswerVariant);
            }
            jsonObject.addProperty("variants", String.valueOf(jsonAnswerVariants));
        }
        return jsonObject;
    }

}
