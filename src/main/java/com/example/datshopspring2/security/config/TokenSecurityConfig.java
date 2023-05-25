package com.example.datshopspring2.security.config;

import com.example.datshopspring2.security.handlers.AuthenticationSuccessHandler;
import com.example.datshopspring2.services.impl.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class TokenSecurityConfig {

    private final UserDetailsService userDetailsServiceImpl;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final OAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/manage-employees/**").hasAuthority("ADMIN")
                .requestMatchers("/manage-product/**").hasAnyAuthority("SELLER", "ADMIN")
                .requestMatchers("/profile/**", "/purchase-history/**").authenticated()
                .requestMatchers("/sign-in", "/sign-up").anonymous()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .usernameParameter("email")
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/sign-in?error")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        httpSecurity
                .oauth2Login()
                .loginPage("/sign-in")
                .userInfoEndpoint().userService(oAuth2Service)
                .and()
                .defaultSuccessUrl("/home")
                .successHandler(authenticationSuccessHandler);

        return httpSecurity.build();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

}
