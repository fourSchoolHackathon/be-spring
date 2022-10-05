package com.hackathon.bespring.global.webpush.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class WebPushSubscribeDto {

    private String endpoint;
    private String auth;
    private String p256dh;
    private String userId;

}
