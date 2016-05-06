package ua.kpi.nc.controller.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.PasswordChangeDto;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by IO on 06.05.2016.
 */
@Controller
@RestController
public class ForgotPassword {

    private UserService userService;
    private SenderService senderService;
    private long expireTme = 60 * 1000 * 60;
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;
    private String secret = "verySecretStringForRecover";

    public ForgotPassword() {
        userService = ServiceFactory.getUserService();
        senderService = SenderServiceImpl.getInstance();
        passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();

    }

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.POST)
    public ResponseEntity<UserDto> sendRecoverURL(@RequestBody UserDto userDto) throws MessagingException {
        String test = userDto.getEmail();
        User user = userService.getUserByUsername(userDto.getEmail());
        if (null == user) {
            //TODO user not found
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        } else {
            String token = createToken(userDto.getEmail());
            String url = "http://localhost:8082/recoverPassword/?token=" + token;
            //TODO load by template;
            String text = url;
            String subject = "Recover your password";
            senderService.send(userDto.getEmail(), subject, text);
            return ResponseEntity.ok(userDto);
        }
    }


    @RequestMapping(value = "/recoverPassword", method = RequestMethod.GET)
    public ResponseEntity<UserDto> doRecover(@RequestParam("token") String token, HttpServletRequest request){
        String email = parseToken(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/frontend/index.html#/recoverPasswordPage");
        if (null == email){
            return new ResponseEntity<UserDto>(null, httpHeaders, HttpStatus.CONFLICT);
        }else {
            request.getSession().setAttribute("email", email);
            UserDto userDto = new UserDto(email);
            return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity<UserDto> doChange(@RequestBody PasswordChangeDto passwordChangeDto, HttpServletRequest request){
        String email = (String) request.getSession().getAttribute("email");
        if (null == email){
            //TODO session has been closed
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        User user = userService.getUserByUsername(email);
        if (null == user){
            //TODO
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else {
            user.setPassword(passwordEncoderGeneratorService.encode(passwordChangeDto.getPassword()));
            userService.updateUser(user);
            //TODO
            return ResponseEntity.ok(null);
        }
    }

    private String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(new Date().getTime() + expireTme))
                .compact();
    }

    public String parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            //TODO log;
        }
        return null;
    }
}
