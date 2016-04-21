package ua.kpi.nc.service.util;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * Created by dima on 21.04.16.
 */
public class AuthenticationManagerService extends AuthenticationManagerBuilder {

    private AuthenticationManagerService(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }

    private static AuthenticationManagerService authenticationManagerBuilder;

    private static ObjectPostProcessor<Object> objectObjectPostProcessor = new ObjectPostProcessor<Object>() {
        @Override
        public <T> T postProcess(T object) {
            return object;
        }
    };

    public static AuthenticationManagerService getAuthenticationManagerBuilder() {
        if (authenticationManagerBuilder == null) {
            authenticationManagerBuilder = new AuthenticationManagerService(objectObjectPostProcessor);
        }
        return authenticationManagerBuilder;
    }

}
