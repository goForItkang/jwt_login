package com.jobdam.jwt_login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter  jwtAuthenticationFilter;
    //password 인코더 하기 위해
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors-> cors.disable())
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 즉 어드민만 가능함
                        .requestMatchers("/user/**").hasRole("USER") // user만 가능 admin도 접속 가능할려면 hasanyroles 로 써야함
                        .requestMatchers("/api/login","/api/signup","/","/api/test/signup").permitAll() // 모든 사용자가 접속 가능해야함
                        .anyRequest().authenticated()) //인증 된사용자
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}
