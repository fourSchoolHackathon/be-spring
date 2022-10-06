package com.hackathon.bespring.infrastructure.feign.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequest {

    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String phoneNumber;
}
