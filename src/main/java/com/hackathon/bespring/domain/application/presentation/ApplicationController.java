package com.hackathon.bespring.domain.application.presentation;

import com.hackathon.bespring.domain.application.presentation.dto.request.DetailsApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.request.UrgentApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.response.PhoneNumberResponse;
import com.hackathon.bespring.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/application")
@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/urgent")
    public PhoneNumberResponse urgentApplication(@RequestBody @Valid UrgentApplicationRequest request) {
        return applicationService.urgentApplication(request);
    }

    @PostMapping
    public void detailsApplication(@RequestBody @Valid DetailsApplicationRequest request) {
        applicationService.detailsApplication(request);
    }
}
