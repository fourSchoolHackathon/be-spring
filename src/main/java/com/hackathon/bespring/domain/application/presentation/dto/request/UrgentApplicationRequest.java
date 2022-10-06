package com.hackathon.bespring.domain.application.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UrgentApplicationRequest {

    @Size(min = 11, max = 11)
    @NotBlank
    private String phoneNumber;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
