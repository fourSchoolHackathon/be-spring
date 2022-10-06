package com.hackathon.bespring.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class LocationRequest {

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
