package com.hackathon.bespring.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationHistoryElement {
    private final String phoneNumber;
    private final LocalDateTime calledAt;
}
