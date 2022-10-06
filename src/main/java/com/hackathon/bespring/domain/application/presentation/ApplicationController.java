package com.hackathon.bespring.domain.application.presentation;

import com.hackathon.bespring.domain.application.presentation.dto.request.CallApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.request.DetailsApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.request.UrgentApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.response.ApplicationHistoryResponse;
import com.hackathon.bespring.domain.application.presentation.dto.response.DetailsApplicationResponse;
import com.hackathon.bespring.domain.application.presentation.dto.response.PhoneNumberResponse;
import com.hackathon.bespring.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public DetailsApplicationResponse detailsApplication(@RequestBody @Valid DetailsApplicationRequest request) {
        return applicationService.detailsApplication(request);
    }

    @PostMapping("/call")
    public void callApplication(@RequestBody @Valid CallApplicationRequest request) {
        applicationService.callApplication(request);
    }

    @GetMapping("/history")
    public List<ApplicationHistoryResponse> callHistoryListApplication() {
        return applicationService.callHistoryListApplication();
    }
}
