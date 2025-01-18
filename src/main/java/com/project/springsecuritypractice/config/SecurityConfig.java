package com.project.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // throws Exception 적는 이유 : authorizeHttpRequests, build가 예외를 던지고 있음.
        httpSecurity
                .csrf((csrf) -> csrf.disable()
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login").loginProcessingUrl("/loginProc").permitAll()
                );

        return httpSecurity.build();
    }
}
