package com.demo.login.iniciosesion.configuration.appConfig;

import com.demo.login.iniciosesion.configuration.filtro.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // permite definir y habilitar la seguridad a nivel de metodo
@RequiredArgsConstructor
@EnableMethodSecurity
public class Securityconfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/saludo/sayHelloPublic",
            "/api/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /*

      cuando se usa el bean de userdetail y el bean de passwordEncoder no es necesario uusar el
      authenticationProvider
    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf().disable()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/login", "/api/registro", "/api/saludo").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
}*/


}
