package ua.kpi.nc.controller.auth;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.model.User;

import java.util.Date;


/**
 * Created by IO on 23.04.2016.
 */
public abstract class TokenHandler{

    protected Long epriretime;
    protected String secret;

    public TokenHandler(){
        this.epriretime = epriretime = 60L * 60L * 1000L;
        this.secret = "verysecret";
    }


    public abstract User parseUserFromToken(String token);

    public abstract String createTokenForUser(UserAuthentication userAuthentication);
}
