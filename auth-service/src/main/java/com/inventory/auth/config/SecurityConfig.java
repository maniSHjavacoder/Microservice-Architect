package com.inventory.auth.config;

import com.inventory.auth.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	 http
         .csrf(csrf -> csrf.disable())   // ✅ FIXED
         .authorizeHttpRequests(auth -> auth
             .requestMatchers(
                 "/auth/login",
                 "/auth/register",
                 "/auth/validate"
             ).permitAll()
             .anyRequest().authenticated()
         )
         .addFilterBefore(
             jwtAuthFilter,
             UsernamePasswordAuthenticationFilter.class
         )
         .formLogin(form -> form.disable())
         .httpBasic(a->a.disable());

     return http.build();
       
    }
}
