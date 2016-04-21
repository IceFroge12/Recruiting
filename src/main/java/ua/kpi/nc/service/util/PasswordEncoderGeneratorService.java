package ua.kpi.nc.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by dima on 20.04.16.
 */
public class PasswordEncoderGeneratorService {

    private static PasswordEncoderGeneratorService passwordEncoderGeneratorService;

    private PasswordEncoderGeneratorService(){

    }

    public static PasswordEncoderGeneratorService getInstance(){
        if(passwordEncoderGeneratorService==null) {
            passwordEncoderGeneratorService = new PasswordEncoderGeneratorService();
        }
        return passwordEncoderGeneratorService;
    }

    public String encode(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

}
