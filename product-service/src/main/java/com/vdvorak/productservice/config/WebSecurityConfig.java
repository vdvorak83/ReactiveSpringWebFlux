package com.vdvorak.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity default config
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/auth/user").hasAnyRole("USER", "ADMIN")
                .pathMatchers("/auth/admin").hasRole("ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .formLogin();
        return http.build();
    }

  /*  @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .formLogin();
        return http.build();
    }*/
}
