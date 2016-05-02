package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;

/**
 * Created by Chalienko on 25.04.2016.
 */
public class GsonFactory {
    public static Gson getApplicationFormGson() {
        return new GsonBuilder().registerTypeAdapter(ApplicationFormImpl.class, new ApplicationFormAdapter())
                .setPrettyPrinting()
                .create();
    }

    public static Gson getUserAdapter(){
        return new GsonBuilder().registerTypeAdapter(UserImpl.class, new UserAdapter())
                .setPrettyPrinting()
                .create();
    }
}
