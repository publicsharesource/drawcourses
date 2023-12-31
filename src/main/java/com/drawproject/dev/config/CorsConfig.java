package com.drawproject.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
                        registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods","Access-Control-Allow-Headers")
                        .allowedMethods("*")
                        .maxAge(1440000);
    }

    //    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                // Allow all origins, headers, and methods for simplicity.
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedHeaders("*")
//                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods","Access-Control-Allow-Headers")
//                        .allowedMethods("*")
//                        .maxAge(1440000);
//            }
//        };
//    }
}
