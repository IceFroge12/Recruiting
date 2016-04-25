package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;

/**
 * Created by Chalienko on 25.04.2016.
 */
public class GsonFactory {
    public static Gson getApplicationFormGson() {
        return new GsonBuilder().registerTypeAdapter(ApplicationFormImpl.class, new ApplicationFormAdapter())
                .setPrettyPrinting()
                .create();
    }
}
