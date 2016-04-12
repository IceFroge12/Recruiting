package ua.kpi.nc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by dima on 12.04.16.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select id,email,password from users where email=? and active = true")
                .authoritiesByUsernameQuery(
                        "select id, role from user_role where id=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/student/**").access("hasRole('ROLE_STUDENT')")
                .antMatchers("/dev/**").access("hasRole('ROLE_DEV')")
                .antMatchers("/hr/**").access("hasRole('ROLE_HR')")
                .antMatchers("/ba/**").access("hasRole('ROLE_BA')")
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }

}