package ua.kpi.nc.controller.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class PasswordEncoderGeneratorService {

    private static class PasswordEncoderGeneratorServiceHolder{
        static PasswordEncoderGeneratorService HOLDER_SERVICE = new PasswordEncoderGeneratorService();
    }

    private BCryptPasswordEncoder passwordEncoder;

    private PasswordEncoderGeneratorService(){
            passwordEncoder = new BCryptPasswordEncoder(-1,new SecureRandom());
    }

    public static PasswordEncoderGeneratorService getInstance(){
        return PasswordEncoderGeneratorServiceHolder.HOLDER_SERVICE;
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String hashPassword, String password){
        return passwordEncoder.matches(password, hashPassword);
    }

}
