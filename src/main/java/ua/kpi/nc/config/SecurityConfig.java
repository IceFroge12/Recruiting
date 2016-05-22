package ua.kpi.nc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.kpi.nc.controller.auth.DataBaseAuthenticationProvider;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.filter.StatelessAuthenticationFilter;
import ua.kpi.nc.filter.StatelessLoginFilter;
import ua.kpi.nc.service.util.UserAuthService;

//import javax.ws.rs.GET;

/**
 * Created by IO on 22.04.16.
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "ua.kpi.nc")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataBaseAuthenticationProvider dataBaseAuthenticationProvider;

    private UserAuthService userAuthService = UserAuthService.getInstance();

    private TokenAuthenticationService tokenAuthenticationService;


    public SecurityConfig() {

        tokenAuthenticationService = new TokenAuthenticationService("verySecret", userAuthService);
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
                .addFilterBefore(new StatelessLoginFilter("/loginIn", tokenAuthenticationService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)



                .exceptionHandling().and()
                .csrf().disable()
                .servletApi().and()
                .headers().cacheControl();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(dataBaseAuthenticationProvider);
    }

    @Override
    protected UserAuthService userDetailsService() {
        return userAuthService;
    }
}