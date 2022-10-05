package com.hackathon.bespring.domain.webpush.service;

import com.hackathon.bespring.domain.webpush.domain.WebPush;
import com.hackathon.bespring.domain.webpush.presentation.dto.request.WebPushSubscribeRequest;
import com.hackathon.bespring.domain.webpush.domain.repository.WebPushRepository;
import com.hackathon.bespring.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebPushService {

    private final UserUtil userUtil;
    private final WebPushRepository webpushRepository;

    public void subscribe(WebPushSubscribeRequest dto) {
        WebPush webPush = webpushRepository.findByUserIdAndEndpoint(userUtil.getCurrentUser().getId(), dto.getEndpoint()).orElseGet(
                () -> WebPush.builder()
                        .endpoint(dto.getEndpoint())
                        .auth(dto.getAuth())
                        .p256dh(dto.getP256dh())
                        .userId(userUtil.getCurrentUser().getId())
                        .build()
        );

        webPush.setEndpoint(dto.getEndpoint());
        webPush.setAuth(dto.getAuth());
        webPush.setP256dh(dto.getP256dh());
        webpushRepository.save(webPush);
    }

    public void unsubscribe() {
        List<WebPush> webPushList = webpushRepository.findAllByUserId(userUtil.getCurrentUser().getId());
        webpushRepository.deleteAll(webPushList);
    }

}
