package com.hackathon.bespring.global.security.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String SecretKey;
    private Long accessExp;
}
