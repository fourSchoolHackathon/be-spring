package com.hackathon.bespring.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class GetUserRequest {

    @NotBlank
    private String phoneNumber;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
