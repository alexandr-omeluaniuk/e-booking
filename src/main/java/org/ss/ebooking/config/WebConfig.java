/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.ebooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.ss.ebooking.constants.AppURLs;

/**
 * Web application configuration.
 * @author Alexandr Omeluaniuk
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /** Available applications. */
    private static final String[] APPLICATIONS = new String[] {
        AppURLs.APP_ADMIN
    };
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(AppURLs.APP_LOGIN_PAGE)
                .addResourceLocations("classpath:/static" + AppURLs.APP_ADMIN + "/build/");
        for (String app : APPLICATIONS) {
            registry.addResourceHandler(app + "/**").addResourceLocations("classpath:/static/" + app + "/build/");
        }
    }
    /**
     * Forward some URLs to index page.
     * Required for React app routing navigation.
     * @return configuration.
     */
    @Bean
    public WebMvcConfigurer forwardToIndex() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController(AppURLs.APP_LOGIN_PAGE)
                        .setViewName("forward:" + AppURLs.APP_ADMIN + "/index.html");
                for (String app : APPLICATIONS) {
                    registry.addViewController(app).setViewName("forward:" + app + "/index.html");
                    registry.addViewController(app + "/view/**").setViewName("forward:" + app + "/index.html");
                }
            }
        };
    }
}
