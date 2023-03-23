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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

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
                            .loginPage("/")
                            .permitAll())
                    .authorizeHttpRequests()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/register/**").permitAll()
                    .requestMatchers("/register-employer/**").permitAll()
                    .requestMatchers("/dashboard/**").permitAll()
                    .requestMatchers("/dashboard-employer/**").permitAll()
                    .requestMatchers("/profile/**").permitAll()
                    .requestMatchers("/edit/**").permitAll()
                    .requestMatchers("/favicon.ico").permitAll()
                    .requestMatchers("/static/**").permitAll()
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login").permitAll();

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
