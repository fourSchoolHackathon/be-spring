package com.hackathon.bespring.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class JwtTokenResolver {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER);
        return parseToken(bearerToken);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.length() > 7 && bearerToken.startsWith(PREFIX)) {
            return bearerToken.replace(PREFIX, "");
        }
        return null;
    }
}
