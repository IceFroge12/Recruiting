package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.*;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chalienko on 24.04.2016.
 */
public class ApplicationFormAdapter implements JsonSerializer<ApplicationForm>, JsonDeserializer<ApplicationForm> {
    @Override
    public ApplicationForm deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        ApplicationForm employee = new ApplicationFormImpl();
//        JsonObject jsonObject = (JsonObject) jsonElement;
//        employee.setId(jsonObject.get("ID").getAsLong());
//        employee.setFirstName(jsonObject.get("first_name").getAsString());
//        employee.setLastName(jsonObject.get("last_name").getAsString());
//        List<Project> projects = new ArrayList<>();
//        for (JsonElement jsonArrayElement : jsonObject.get("projects").getAsJsonArray()){
//            JsonObject jsonProject = (JsonObject) jsonArrayElement;
//            projects.add(new ProjectProxy(jsonProject.get("ID").getAsLong()));
//        }
//        employee.setProjects(projects);
        return employee;
    }

    @Override
    public JsonElement serialize(ApplicationForm applicationForm, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("ID", employee.getId());
//        jsonObject.addProperty("first_name", employee.getFirstName());
//        jsonObject.addProperty("last_name", employee.getLastName());
//        JsonArray jsonProjectsArray = new JsonArray();
//        for(Project project : employee.getProjects()){
//            JsonObject jsonProject = new JsonObject();
//            jsonProject.addProperty("ID", project.getId());
//            jsonProjectsArray.add(jsonProject);
//        }
//        jsonObject.add("projects", jsonProjectsArray);
        return jsonObject;
    }
}