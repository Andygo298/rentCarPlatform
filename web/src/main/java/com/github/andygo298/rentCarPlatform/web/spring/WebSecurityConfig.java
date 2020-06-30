package com.github.andygo298.rentCarPlatform.web.spring;

import com.github.andygo298.rentCarPlatform.web.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/login",
                        "/register**",
                        "/logout-user",
                        "/error**").permitAll()
                .antMatchers(
                        "/maintenance",
                        "/maintenance/list",
                        "/maintenance/list/addWorker",
                        "/maintenance/list/editWorker",
                        "/maintenance/list/delWorker",
                        "/maintenance/list/delWorker",
                        "/maintenance/list/addWorkersToCar",
                        "/maintenance/list/remove",
                        "/home/addCar",
                        "/home/editCar",
                        "/home/deleteCar").hasRole("ADMIN")
                .antMatchers(
                        "/home",
                        "/orders** ",
                        "/payment** ").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }
}
