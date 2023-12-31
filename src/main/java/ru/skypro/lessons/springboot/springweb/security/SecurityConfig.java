package ru.skypro.lessons.springboot.springweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.lessons.springboot.springweb.entity.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .sessionManagement(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        matcherRegistry ->
                                matcherRegistry
                                        .requestMatchers(HttpMethod.POST, "/employee/**", "/report/**")
                                        .hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.PUT, "/employee/**").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.DELETE, "/employee/**").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.GET, "/employee/**", "/report/**")
                                        .hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                        .requestMatchers("/**").permitAll()
                )
                .build();
    }
}
