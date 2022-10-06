package com.hackathon.bespring.domain.application.presentation.dto.request;

import com.hackathon.bespring.domain.category.domain.Categories;
import com.hackathon.bespring.domain.user.domain.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class DetailsApplicationRequest {

    @Size(min = 11, max = 11)
    @NotBlank
    private String phoneNumber;

    @NotNull
    private Sex sex;

    @NotNull
    private List<Categories> categoryList;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
