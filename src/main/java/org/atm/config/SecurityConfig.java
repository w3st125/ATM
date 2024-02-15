package org.atm.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.atm.service.UserService;
import org.atm.web.security.TokenAuthenticationFilter;
import org.atm.web.security.TokenAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenAuthenticationManager tokenAuthenticationManager;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*http.addFilterAfter(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();*/
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(
                        cors ->
                                cors.configurationSource(
                                        request -> {
                                            var corsConfiguration = new CorsConfiguration();
                                            corsConfiguration.setAllowedOriginPatterns(
                                                    List.of("*"));
                                            corsConfiguration.setAllowedMethods(
                                                    List.of(
                                                            "GET", "POST", "PUT", "DELETE",
                                                            "OPTIONS"));
                                            corsConfiguration.setAllowedHeaders(List.of("*"));
                                            corsConfiguration.setAllowCredentials(true);
                                            return corsConfiguration;
                                        }))
                // Настройка доступа к конечным точкам
                .authorizeHttpRequests(
                        request ->
                                request
                                        // Можно указать конкретный путь, * - 1 уровень вложенности,
                                        // ** - любое количество уровней вложенности
                                        .requestMatchers(antMatcher("/user/login"))
                                        .permitAll()
                                        /*.requestMatchers(antMatcher("/operation/for-admin/{login}"))
                                        .hasAuthority("ADMIN")*/
                                        .anyRequest()
                                        .authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        /*authProvider.setPasswordEncoder(passwordEncoder());*/
        return authProvider;
    }

    /* @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}
