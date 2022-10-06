package com.hackathon.bespring.domain.user.presentation;

import com.hackathon.bespring.domain.application.presentation.dto.response.ApplicationHistoryResponse;
import com.hackathon.bespring.domain.user.presentation.dto.request.LocationRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignInRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignUpRequest;
import com.hackathon.bespring.domain.user.presentation.dto.response.TokenResponse;
import com.hackathon.bespring.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public TokenResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody @Valid SignInRequest request) {
        return userService.signIn(request);
    }

    @PatchMapping
    public void updateLocation(@RequestBody @Valid LocationRequest request) {
        userService.updateLocation(request);
    }

    @GetMapping("/history")
    public List<ApplicationHistoryResponse> callHistoryListApplication() {
        return userService.callHistoryListApplication();
    }

}
