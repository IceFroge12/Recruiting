package ua.kpi.nc.controller;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ua.kpi.nc.service.SenderService;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.impl.Google2Api;
import javax.annotation.Resource;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

/**
 * Created by dima on 13.04.16.
 */

@Controller
public class GoogleAuthController {

    @Autowired
    UserService userService;

    @Autowired
    SenderService senderService;

    @Resource
    private Environment env;

    private static final String NETWORK_NAME = "GOOGLE";

    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";

    private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

    private static final Token EMPTY_TOKEN = null;

    String apiKey = "google.app.id";
    String apiSecret = "google.app.secret";
    String callbackUrl = "google.app.redirect.url";

    OAuthService service = new ServiceBuilder().provider(Google2Api.class)
            .apiKey(env.getRequiredProperty(apiKey)).apiSecret(env.getRequiredProperty(apiSecret)).callback(env.getRequiredProperty(callbackUrl))
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

        return "redirect:/student";
    }


}