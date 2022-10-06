package com.hackathon.bespring.domain.application.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationHistoryResponse {

    private final String phoneNumber;
    private final LocalDateTime calledAt;

}
