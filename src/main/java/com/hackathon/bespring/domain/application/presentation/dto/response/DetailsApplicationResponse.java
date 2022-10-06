package com.hackathon.bespring.domain.application.presentation.dto.response;

import com.hackathon.bespring.domain.category.domain.Categories;
import com.hackathon.bespring.domain.user.domain.Sex;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DetailsApplicationResponse {

    private final String phoneNumber;
    private final Sex sex;
    private final List<Categories> categoryList;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final BigDecimal longitude;
    private final BigDecimal latitude;
}
