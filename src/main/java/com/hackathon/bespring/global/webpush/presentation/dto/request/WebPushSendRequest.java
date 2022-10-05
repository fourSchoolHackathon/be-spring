package com.hackathon.bespring.global.webpush.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebPushSendRequest {

    private String title;
    private String body;
    private String link;

}
