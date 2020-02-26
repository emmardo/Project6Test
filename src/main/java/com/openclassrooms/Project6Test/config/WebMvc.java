package com.openclassrooms.Project6Test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebMvc implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {

        //changed setViewName from "login" to "home"
        registry.addViewController("/").setViewName("home");
    }
}
