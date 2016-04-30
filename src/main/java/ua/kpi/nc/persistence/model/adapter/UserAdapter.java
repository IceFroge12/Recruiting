package ua.kpi.nc.persistence.model.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;

import java.io.IOException;

/**
 * Created by IO on 30.04.2016.
 */
//TODO need complete
public class UserAdapter extends TypeAdapter<UserImpl> {


    public UserAdapter() {

    }

    @Override
    public void write(JsonWriter jsonWriter, UserImpl user) throws IOException {
        //TODO complete
    }

    @Override
    public UserImpl read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        UserImpl user = new UserImpl();
        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "email":
                    user.setEmail(jsonReader.nextString());
                    break;
                case "password":
                    user.setPassword(jsonReader.nextString());
                    break;
                //TODO complete
            }
        }
        jsonReader.endObject();
        return user;
    }
}
