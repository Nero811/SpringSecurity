package com.example.demo.movie_security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.movie_security.filter.UserLoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors
                        .configurationSource(createCorsConfig()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .addFilterAfter(new UserLoginFilter(), AuthorizationFilter.class)

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        // 註冊帳號功能
                        .requestMatchers("/register").permitAll()
                        // 登入功能
                        .requestMatchers("/userLogin").authenticated()
                        // Movie 功能
                        .requestMatchers("/getMovies")
                        .hasAnyAuthority("ROLE_NORMAL_MEMBER", "ROLE_MOVIE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers("/watchFreeMovie").hasAnyAuthority("ROLE_NORMAL_MEMBER", "ROLE_ADMIN")
                        .requestMatchers("/watchVipMovie").hasAnyAuthority("ROLE_VIP_MEMBER", "ROLE_ADMIN")
                        .requestMatchers("/uploadMovie").hasAnyAuthority("ROLE_MOVIE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers("/deleteMovie").hasAnyAuthority("ROLE_MOVIE_MANAGER", "ROLE_ADMIN")
                        // 註冊功能
                        .requestMatchers("/subscribe", "/unSubscribe")
                        .hasAnyAuthority("ROLE_NORMAL_MEMBER", "ROLE_ADMIN")
                        .anyRequest().denyAll())
                .build();
    }

    private CorsConfigurationSource createCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // 表示後端允許請求來源有哪些，*表示允許所有來源
        config.setAllowedHeaders(List.of("*")); // 表示後端允許的Request Header有哪些，*表示允許所有來源
        config.setAllowedMethods(List.of("*")); // 表示後端允許的Http Method有哪些，*表示允許所有來源
        // config.setAllowCredentials(true); // 表示後端是否允許前端帶上Cookie
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
