package com.testing.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Basic Spring Security configuration.
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disabling CSRF for this example as we're using REST APIs.
                .csrf(AbstractHttpConfigurer::disable)
                // Configure security headers for XSS and Clickjacking protection
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin() // Allow H2 console in same origin frame
                        )
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net;")
                        )
                        // Fix: use the enum instead of a raw string
                        .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                )
                .authorizeHttpRequests(authorize -> authorize
                        // Use AntPathRequestMatcher explicitly to avoid ambiguity with multiple servlets
                        .requestMatchers(
                            new AntPathRequestMatcher("/api/users/login"),
                            new AntPathRequestMatcher("/api/users/register"),
                            new AntPathRequestMatcher("/h2-console/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
