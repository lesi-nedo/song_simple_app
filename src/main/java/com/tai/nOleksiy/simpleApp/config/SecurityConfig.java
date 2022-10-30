package com.tai.nOleksiy.simpleApp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest()
                .permitAll()
                .and().csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        return httpSecurity.build();
    }

    @Bean
    public HttpFirewall getHttpFirewall () {
        StrictHttpFirewall strictHttpFir = new StrictHttpFirewall();
        strictHttpFir.setAllowSemicolon(true);
        return strictHttpFir;
    }
}

