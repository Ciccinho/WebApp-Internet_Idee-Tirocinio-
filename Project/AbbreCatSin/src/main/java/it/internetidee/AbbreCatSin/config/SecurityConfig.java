package it.internetidee.AbbreCatSin.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request->{                                                       //configurazione CORS
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOrigin("http://localhost:4200");                                      //origine abilitazione richieste
                configuration.setAllowedMethods(List.of("GET","POST","PUT", "DELETE", "OPTION"));     //richieste permesse
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);
                return configuration;
            })).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()                        //percorsi abilitati per le richieste
                .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/auth/getAnagrafica/").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/catastoSintetico").authenticated()
                .requestMatchers("/error").permitAll()
                .anyRequest().denyAll());
        return http.build();
    }
}