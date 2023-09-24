package com.intakhab.studentteacherappointmentbooking.Config;

import com.intakhab.studentteacherappointmentbooking.Service.CustomUserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    CustomUserDetailsServices customUserDetailsServices;
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/forgot","/otp").permitAll()
                .requestMatchers("/register", "/home").permitAll() // Allow these endpoints for all users
                .requestMatchers("/student/**").hasRole("STUDENT")// Require ROLE_STUDENT for /student/** endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")     // Require ROLE_ADMIN for /admin/** endpoints
                .requestMatchers("/teacher/**").hasRole("TEACHER") // Require ROLE_TEACHER for /teacher/** endpoints
                .anyRequest().authenticated() // Require authentication for any other endpoint
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home", true).permitAll()
                .successHandler(customSuccessHandler)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll();

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
    }
}
