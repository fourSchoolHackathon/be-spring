package com.hackathon.bespring.domain.application.presentation.dto.response;

import com.hackathon.bespring.domain.user.presentation.dto.response.ApplicationHistoryElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicationHistoryResponse {

    private final String name;
    private final List<ApplicationHistoryElement> applicationList;

}
