package com.hackathon.bespring.domain.webpush.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class WebPushSubscribeRequest {

    private String endpoint;
    private String auth;
    private String p256dh;

}
