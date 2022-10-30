package com.tai.nOleksiy.simpleApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.tai"})
@EnableConfigurationProperties({WebConfigProperties.class})
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private WebConfigProperties webConfigProperties;
    @Override
    public void configurePathMatch(PathMatchConfigurer conf) {
        UrlPathHelper url = new UrlPathHelper();

        url.setRemoveSemicolonContent(false);
        conf.setUrlPathHelper(url);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> enableDefaultServlet(){
        return (factory) -> factory.setRegisterDefaultServlet(true);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                WebConfigProperties.Cors cors = webConfigProperties.getCors();
                System.out.println(Arrays.asList(cors.getAllowedOrigins()));
                registry.addMapping("/**")
                        .allowedOrigins(cors.getAllowedOrigins())
                        .allowedMethods(cors.getAllowedMethods());
            }
        };
    }
}

