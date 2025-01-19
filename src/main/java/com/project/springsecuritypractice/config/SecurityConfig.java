package com.project.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // throws Exception 적는 이유 : authorizeHttpRequests, build가 예외를 던지고 있음.
        httpSecurity
                // CSRF 비활성화.
                .csrf((csrf) -> csrf.disable()
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/login", "/join", "/loginProc", "/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                // 권한이 필요한 URL 요청 시 /login으로 리다이렉트 시킴.
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login").loginProcessingUrl("/loginProc").permitAll()
                );
        return httpSecurity.build();
    }
}
