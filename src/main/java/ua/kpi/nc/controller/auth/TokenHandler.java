package ua.kpi.nc.controller.auth;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.config.PropertiesReader;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;

import java.util.Date;


/**
 * Created by IO on 23.04.2016.
 */
public abstract class TokenHandler {

    protected Long epriretime;
    protected String secret;
    private PropertiesReader propertiesReader = PropertiesReader.getInstance();

    public TokenHandler() {
        this.epriretime = Long.parseLong(propertiesReader.propertiesReader("session.expireTime"));
        this.secret = propertiesReader.propertiesReader("token.secret");
    }


    public abstract UserAuthentication parseUserFromToken(String token);

    public abstract String createTokenForUser(UserAuthentication userAuthentication);

    protected String parse(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    protected String createToken(String token) {
        return Jwts.builder()
                .setSubject(token)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(new Date().getTime() + epriretime))
                .compact();
    }

}
