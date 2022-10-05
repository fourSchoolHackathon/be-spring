package com.hackathon.bespring.domain.user.presentation.dto.request;

import com.hackathon.bespring.domain.category.domain.Categories;
import com.hackathon.bespring.domain.user.domain.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 6, max = 24)
    private String accountId;

    @NotBlank
    @Size(min = 2, max = 5)
    private String name;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,24}$",
            message = "password는 대문자[A-Z], 소문자[a-z], 숫자[0-9], 특수문자[!, @, #, $, %, ^, &, +, =]가 최소 1개씩 포함되어야 합니다.")
    private String password;

    @NotNull
    private Sex sex;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotNull
    private List<Long> categories;
}
