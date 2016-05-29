package ua.kpi.nc.controller.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.model.User;

import java.util.Date;

/**
 * Created by IO on 29.05.2016.
 */
public class TokenHandlerSocial extends TokenHandler {

    private UserAuthServiceSocial userAuthServiceSocial;
    private Logger log = LoggerFactory.getLogger(TokenHandlerSocial.class);

    private static class TokenHandlerSocialHolder{
        private static final TokenHandlerSocial HOLDER = new TokenHandlerSocial();
    }

    public static TokenHandlerSocial getInstance(){
        return TokenHandlerSocialHolder.HOLDER;
    }

    private TokenHandlerSocial() {
        super();
        userAuthServiceSocial = UserAuthServiceSocial.getInstance();
        log = LoggerFactory.getLogger(TokenHandlerSocial.class);
    }

    @Override
    public User parseUserFromToken(String token) {
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            JsonObject jsonObject = ((JsonObject) new JsonParser().parse(claims.getSubject()));
            return userAuthServiceSocial.loadUserBySocialIdNetworkId(jsonObject.get("idUserSocial").getAsLong(), jsonObject.get("idNetwork").getAsLong());
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        }
        return null;
    }

    @Override
    public String createTokenForUser(UserAuthentication userAuthentication) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idUserSocial", userAuthentication.getIdUserSocialNetwork());
        jsonObject.addProperty("idNetwork", userAuthentication.getIdNetwork());
        return Jwts.builder()
                .setSubject(jsonObject.toString())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(new Date().getTime() + epriretime))
                .compact();
    }
}
