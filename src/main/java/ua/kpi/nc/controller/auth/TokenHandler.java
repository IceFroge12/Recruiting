package ua.kpi.nc.controller.auth;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.UserAuthService;

import java.util.Date;


/**
 * Created by IO on 23.04.2016.
 */
public class TokenHandler {

    private final String secret;
    private final UserAuthService userService;

    private static Logger log = LoggerFactory.getLogger(TokenHandler.class);

    private final long epriretime = 60 * 1000 * 5;

    public TokenHandler(String secret, UserAuthService userAuthService) {
        this.secret = StringConditions.checkNotBlank(secret);
        this.userService = Preconditions.checkNotNull(userAuthService);
    }

    public User parseUserFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return userService.loadUserByUsername(claims.getSubject());
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        }
        return null;
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(new Date().getTime() + epriretime))
                .compact();
    }
}
