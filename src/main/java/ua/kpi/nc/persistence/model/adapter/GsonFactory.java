package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;

/**
 * Created by Chalienko on 25.04.2016.
 */
public class GsonFactory {
    private static Gson applicationFormGson;
    private static Gson userGson;
    private static Gson formQuestionGson;

    public static Gson getApplicationFormGson() {
        if (applicationFormGson == null){
            applicationFormGson = new GsonBuilder().registerTypeAdapter(ApplicationFormImpl.class, new ApplicationFormAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return applicationFormGson;
    }

    public static Gson getUserGson(){
        if (userGson == null) {
            userGson = new GsonBuilder().registerTypeAdapter(UserImpl.class, new UserAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return userGson;
    }

    public static Gson getFormQuestionGson() {
        if (formQuestionGson == null) {
            formQuestionGson = new GsonBuilder().registerTypeAdapter(FormQuestionImpl.class, new FormQuestionAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return formQuestionGson;
    }
}
