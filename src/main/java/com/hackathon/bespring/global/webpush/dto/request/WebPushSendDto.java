package com.hackathon.bespring.global.webpush.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class WebPushSendDto {

    private String msg;

}
