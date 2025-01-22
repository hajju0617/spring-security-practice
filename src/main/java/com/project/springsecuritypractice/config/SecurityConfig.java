package com.project.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

//                .httpBasic(Customizer.withDefaults()      // http basic 인증 방식.
                );

//        httpSecurity.securityMatcher("/")
//                .authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("/").permitAll());

        return httpSecurity.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain2(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.securityMatcher("/admin")
//                .authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("/admin").authenticated());
//        return httpSecurity.build();
//    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("userA")
                .password(bCryptPasswordEncoder().encode("user1"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("userB")
                .password(bCryptPasswordEncoder().encode("user2"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

//    @Bean
//    public RoleHierarchy roleHierarchy() {
//
//        return RoleHierarchyImpl.fromHierarchy(
//                """
//                ROLE_C > ROLE_B
//                ROLE_B > ROLE_A
//                """
//        );
//    }

}
