package ua.kpi.nc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.kpi.nc.controller.auth.*;
import ua.kpi.nc.filter.SocialLoginFilter;
import ua.kpi.nc.filter.StatelessAuthenticationFilter;
import ua.kpi.nc.filter.StatelessLoginFilter;
import ua.kpi.nc.persistence.model.SocialNetwork;


/**
 * Created by IO on 22.04.16.
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "ua.kpi.nc")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SECRET_KEY = "verySecret";

    private LoginPasswordAuthenticationManager loginPasswordAuthenticationManager = LoginPasswordAuthenticationManager.getInstance();

    private SocialNetworkAuthenticationManager socialNetworkAuthenticationManager = SocialNetworkAuthenticationManager.getInstance();

    private AuthenticationSuccessHandler authenticationSuccessHandler = AuthenticationSuccessHandlerService.getInstance();

    private UserAuthService userAuthService = UserAuthService.getInstance();

    private TokenAuthenticationService tokenAuthenticationService = new TokenAuthenticationService(SECRET_KEY, userAuthService);



    public SecurityConfig() {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/home").anonymous()

                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/admin/view/**").hasRole("ADMIN")
                .antMatchers("**/reports/**").hasRole("ADMIN")

                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/student/view/**").hasRole("STUDENT")
                .antMatchers("/student/appform/**").permitAll()
                .antMatchers("/student/**").hasRole("STUDENT")

                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/staff/view/**").hasAnyRole("SOFT", "TECH")
                .antMatchers("/staff/appForm/**").permitAll()
                .antMatchers("/staff/**").hasAnyRole("SOFT", "TECH")

                .and()

                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new StatelessLoginFilter("/loginIn", tokenAuthenticationService, loginPasswordAuthenticationManager, authenticationSuccessHandler), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SocialLoginFilter(new AntPathRequestMatcher("/socialAuth/**"), socialNetworkAuthenticationManager, authenticationSuccessHandler), UsernamePasswordAuthenticationFilter.class)


                .exceptionHandling().and()
                .csrf().disable()
                .servletApi().and()
                .headers().cacheControl();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
    }

}