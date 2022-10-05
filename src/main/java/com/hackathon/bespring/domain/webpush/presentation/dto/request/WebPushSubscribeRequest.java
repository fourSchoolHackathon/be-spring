package com.hackathon.bespring.domain.webpush.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class WebPushSubscribeRequest {

    @NotBlank
    private String endpoint;

    @NotBlank
    private String auth;

    @NotBlank
    private String p256dh;

}
