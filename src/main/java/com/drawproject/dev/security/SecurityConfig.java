package com.drawproject.dev.security;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint authEntryPoint;
    private CustomUserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                 .requestMatchers(
                         "/swagger-resources/**",
                         "/configuration/security",
                         "/swagger-ui/**",
                         "/webjars/**"
                 ).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
//                 .requestMatchers("/api/top-courses/**").permitAll()
//                 .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
//                 .requestMatchers(HttpMethod.POST, "/api/courses").hasRole("INSTRUCTOR")
//                 .requestMatchers(HttpMethod.PUT, "/api/courses").hasRole("INSTRUCTOR")
//                 .requestMatchers(HttpMethod.DELETE, "/api/courses/{id}").hasAnyRole(
//                         DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF, DrawProjectConstaints.INSTRUCTOR
//                 )
//                 .requestMatchers(HttpMethod.GET, "/api/viewcourses").hasAnyRole(
//                         DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE
//                 )
//                 .requestMatchers(HttpMethod.GET, "/api/courses/{id}/student").hasAnyRole(
//                         DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.INSTRUCTOR
//                 )
//                 .requestMatchers(HttpMethod.GET, "/api/courses/{id}/feedback").permitAll()
//                 .requestMatchers(HttpMethod.PUT, "/api/courses/{id}/feedback").hasRole(DrawProjectConstaints.USER_ROLE)
//                 .requestMatchers(HttpMethod.POST, "/api/courses/{id}/feedback").hasRole(DrawProjectConstaints.USER_ROLE)
//                 .requestMatchers(HttpMethod.DELETE, "/api/courses/{id}/feedback/{feedbackId}").hasAnyRole(
//                         DrawProjectConstaints.USER_ROLE, DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE
//                 )
//                 .requestMatchers(HttpMethod.GET, "/api/courses/{id}/topic").permitAll()
//                 .requestMatchers(HttpMethod.POST, "/api/courses/{id}/topic").hasRole(DrawProjectConstaints.INSTRUCTOR)
//                 .requestMatchers(HttpMethod.GET, "/api//users/{id}/courses").hasAnyRole(
//                         DrawProjectConstaints.USER_ROLE, DrawProjectConstaints.INSTRUCTOR
//                 )
                 .requestMatchers("/api/v1/dashboard").authenticated()
                 .requestMatchers("/api/v1/instructor/**").permitAll()
                 .requestMatchers("/api/v1/profile/**").authenticated()
                 .requestMatchers("/api/v1/cart/**").authenticated()
                 .requestMatchers("/api/v1/category/**").permitAll()
                 .requestMatchers("/api/v1/skill/**").permitAll()
                 .requestMatchers("/api/v1/comment/**").permitAll()
                 .requestMatchers(HttpMethod.GET,"/api/v1/post/{postId}").permitAll()
                 .requestMatchers(HttpMethod.GET,"/api/v1/profile/posts").authenticated()
                 .requestMatchers(HttpMethod.GET,"/api/v1/post").permitAll()
                 .requestMatchers(HttpMethod.POST,"/api/v1/post").authenticated()
                 .requestMatchers(HttpMethod.PUT,"/api/v1/post/{id}").authenticated()
                 .requestMatchers("/api/v1/post/deletePost").hasRole("ADMIN")
                 .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                 .requestMatchers(HttpMethod.POST,"/api/v1/contact").permitAll()
                 .requestMatchers(HttpMethod.GET,"api/v1/contact").hasRole("ADMIN")
                 .requestMatchers(HttpMethod.PUT,"/api/v1/contact/{id}").hasRole("ADMIN")
                 .requestMatchers(HttpMethod.POST,"/api/v1/pay").authenticated()
                 .requestMatchers(HttpMethod.GET,"/api/v1/pay/cancel").permitAll()
                 .requestMatchers(HttpMethod.GET,"/api/v1/pay/success").authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
