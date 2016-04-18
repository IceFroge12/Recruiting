package ua.kpi.nc.service.impl;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.nc.service.PasswordEncoderGeneratorService;

/**
 * Created by dima on 16.04.16.
 */
@Service
public class PasswordEncoderGeneratorImpl implements PasswordEncoderGeneratorService {

    @Override
    public String encode(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

}
