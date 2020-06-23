package com.github.andygo298.rentCarPlatform.web.spring;

import com.github.andygo298.rentCarPlatform.service.config.ServiceConfig;
import com.github.andygo298.rentCarPlatform.web.controller.*;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfig {

    private ServiceConfig serviceConfig;

    public WebConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
//controllers:
    @Bean
    LoginController loginController(){
        return new LoginController(serviceConfig.securityService());
    }

    @Bean
    LogoutController logoutController(){
        return new LogoutController();
    }

    @Bean
    RegisterController registerController(){
        return new RegisterController(serviceConfig.securityService(),serviceConfig.userService());
    }
    @Bean
    CarController carController(){
        return new CarController(serviceConfig.carService(),serviceConfig.orderService());
    }
    @Bean
    OrderController orderController(){
        return new OrderController(serviceConfig.orderService(),serviceConfig.carService());
    }




    @Bean
    public UrlBasedViewResolver tilesViewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        return tilesConfigurer;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:i18n/messages");
        source.setDefaultEncoding("UTF-8");

        return source;
    }

    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("en"));
        resolver.setCookieName("LocaleCookie");
        resolver.setCookieMaxAge(3600);
        return resolver;
    }
}
