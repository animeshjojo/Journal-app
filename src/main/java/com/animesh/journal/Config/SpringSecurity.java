package com.animesh.journal.Config;


import com.animesh.journal.Filter.JwtFilter;
import com.animesh.journal.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //This helps to customize security
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/journal/**", "/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}

/*
Flow-->
1)Request arrives with Basic auth header.
2)Spring Security extracts username + password.
3)Calls your UserDetailsServiceImpl.loadUserByUsername().
4)You fetch user from DB and return username, hashed password, roles.
5)Spring compares raw password with hashed password using BCrypt.
6)If match → user authenticated.
7)Spring checks roles for the requested URL.
8)If allowed → controller executes.
*/

/*
CSRF (Cross-Site Request Forgery) is an attack where a malicious website tricks a logged-in user’s browser into sending requests (like POST/DELETE) to another site without the user knowing.
This works only when the browser automatically sends cookies.

Spring Security protects against this by requiring a CSRF token for such requests.

But in REST APIs, we usually use JWT or Basic Auth, not cookies — the browser does not automatically send anything.
So CSRF attacks cannot happen, and CSRF protection becomes unnecessary.

👉 Therefore, we disable CSRF for REST APIs.
*/
