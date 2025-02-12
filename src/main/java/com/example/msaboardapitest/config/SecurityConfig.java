package com.example.msaboardapitest.config;

import com.example.msaboardapitest.config.scanner.RequestPathScanner;
import com.example.msaboardapitest.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Map;
import java.util.Set;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final RequestPathScanner scanner;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        Map<RoleType, Set<String>> requestPaths = scanner.scanRequestMethods();
        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/images/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/profile")
                        ).permitAll()
                        .requestMatchers(requestPaths.get(RoleType.ALL).toArray(new String[0])).permitAll()
                        .requestMatchers(requestPaths.get(RoleType.CUSTOMER).toArray(new String[0])).hasRole(RoleType.CUSTOMER.name())
                        .requestMatchers(requestPaths.get(RoleType.ADMIN).toArray(new String[0])).hasRole(RoleType.ADMIN.name())
                        .requestMatchers(requestPaths.get(RoleType.SUPER_ADMIN).toArray(new String[0])).hasRole(RoleType.SUPER_ADMIN.name())
                        .anyRequest().authenticated())
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/resources/**")
                .requestMatchers("/static/**")
                .requestMatchers("/swagger-ui/**")
                .requestMatchers("/v3/api-docs/**")
                .requestMatchers("/swagger-resources/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}


