package com.akmz.jep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig{
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
            .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 인증 인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/user/login2"),
                                new AntPathRequestMatcher("/api/user/create"),
                                new AntPathRequestMatcher("/docs"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/api-docs/**"),
                                new AntPathRequestMatcher("/test")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // csfr 비활성화 운영단계에서는 활성화 해야함.
                .csrf(AbstractHttpConfigurer::disable).build();
    }
}
