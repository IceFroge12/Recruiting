package ua.kpi.nc.controller.auth;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.UserAuthService;


/**
 * Created by IO on 23.04.2016.
 */
public class TokenHandler {

    private final String secret;
    private final UserAuthService userService;

    //TODO need replace in property file
    private final long epriretime = 60 * 1000 * 5;

    public TokenHandler(String secret, UserAuthService userAuthService) {
        this.secret = StringConditions.checkNotBlank(secret);
        this.userService = Preconditions.checkNotNull(userAuthService);
    }

    public User parseUserFromToken(String token) {
        Claims claims =  Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        String name = claims.getSubject();
        return userService.loadUserByUsername(claims.getSubject());
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
