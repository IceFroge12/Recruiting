package ua.kpi.nc.controller.auth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ua.kpi.nc.service.SenderService;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.impl.Google2Api;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

/**
 * Created by dima on 13.04.16.
 */

@Controller
public class GoogleAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SenderService senderService;


    private static final String NETWORK_NAME = "GOOGLE";

    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";

    private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

    private static final Token EMPTY_TOKEN = null;


    @Value("${google.app.id}")
    private String apiKey;
    @Value("${google.app.secret}")
    private String apiSecret;
    @Value("${google.app.redirect.url}")
    private String callbackUrl;



    private OAuthService service = new ServiceBuilder().provider(Google2Api.class)
            .apiKey("379040143751-9bj3nc6b1q9q4cvmdschkme5aqtsi7b8.apps.googleusercontent.com")
            .apiSecret("lr6ogmceXGcwZKhY4krvKNRY").callback("http://localhost:8080/googlecallback")
            .scope(SCOPE).build();


    @RequestMapping(value = "/google-auth", method = RequestMethod.GET)
    public String googleAuth(WebRequest request) {
        Token accessToken = (Token) request.getAttribute("ATTR_OAUTH_ACCESS_TOKEN", SCOPE_SESSION);
        if (accessToken == null) {
            String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
            return "redirect:" + authorizationUrl;
        }
        return "student";
    }


    @RequestMapping(value = {"/googlecallback"}, method = RequestMethod.GET)
    public String callBack(@RequestParam("code") String oauthVerifier) {
        Verifier verifier = new Verifier(oauthVerifier);

        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);

        OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, oauthRequest);
        Response response = oauthRequest.send();
        System.out.println(response.getBody().toString());

        String userName = null;//TODO

        if(userService.isExist(userName)){
            //TODO
            return "redirect:/student";
        }else {
            //TODO
            return "redirect:/student";
        }

    }


}