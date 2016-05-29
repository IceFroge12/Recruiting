package ua.kpi.nc.controller.auth.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.model.SocialInformation;

/**
 * Created by IO on 29.05.2016.
 */
public class FaceBookUtils {

    private static String USER_ID = "userID";
    private static String ACCESS_TOKEN_TITLE = "accessToken";

    public static Long getSocialUserId(String info){
        return ((JsonObject) new JsonParser().parse(info)).get(USER_ID).getAsLong();
    }

    public static  String getFaceBookAccessToken(String info) {
        return ((JsonObject) new JsonParser().parse(info)).get(ACCESS_TOKEN_TITLE).getAsString();
    }
}
