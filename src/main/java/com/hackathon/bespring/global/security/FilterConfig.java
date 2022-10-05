package com.hackathon.bespring.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.bespring.global.error.ErrorFilter;
import com.hackathon.bespring.global.security.jwt.JwtTokenFilter;
import com.hackathon.bespring.global.security.jwt.JwtTokenProvider;
import com.hackathon.bespring.global.security.jwt.JwtTokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenResolver jwtTokenResolver;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity builder) {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenResolver, jwtTokenProvider);
        ErrorFilter errorFilter = new ErrorFilter(objectMapper);
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(errorFilter, JwtTokenFilter.class);
    }
}
