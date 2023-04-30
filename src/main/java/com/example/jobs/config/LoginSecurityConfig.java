package com.example.jobs.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class LoginSecurityConfig {

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity http) throws Exception {

        try {
            http
                    .csrf().disable()
                    .formLogin(form -> form
                            .loginPage("/login")
                            .permitAll())
                    .authorizeHttpRequests()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/register/**").permitAll()
                    .requestMatchers("/register-company/**").permitAll()
                    .requestMatchers("/register-candidate/**").permitAll()
                    .requestMatchers("/dashboard/**").permitAll()
                    .requestMatchers("/dashboard-company/**").permitAll()
                    .requestMatchers("/profile/**").permitAll()
                    .requestMatchers("/edit/**").permitAll()
                    .requestMatchers("/announcement/**").permitAll()
                    .requestMatchers("/jobs-company/**").permitAll()
                    .requestMatchers("/add-announcement/**").permitAll()
                    .requestMatchers("/favicon.ico").permitAll()
                    .requestMatchers("/static/**").permitAll()
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/main-page").permitAll();

        } catch (Exception e) {
            log.error("Security configuration failed", e);
        }
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation().migrateSession();

        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
