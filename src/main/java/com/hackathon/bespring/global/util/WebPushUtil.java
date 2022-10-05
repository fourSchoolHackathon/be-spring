package com.hackathon.bespring.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.bespring.domain.webpush.domain.WebPush;
import com.hackathon.bespring.domain.webpush.presentation.dto.request.WebPushSendDto;
import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WebPushUtil {

    private final ObjectMapper objectMapper;
    private PushService pushService;

    @Value("${web-push.key.public}")
    private String publicKey;
    @Value("${web-push.key.private}")
    private String privateKey;

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public void sendNotification(WebPush webPush, WebPushSendDto dto) {
        Subscription subscription = new Subscription(webPush.getEndpoint(), new Subscription.Keys(webPush.getP256dh(), webPush.getAuth()));
        try {
            pushService.send(new Notification(subscription, objectMapper.writeValueAsString(dto)));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void sendNotificationToAll(List<WebPush> webPushList, WebPushSendDto dto) throws JsonProcessingException {
        List<Subscription> subscriptionList = webPushList.stream()
                .map(webPush -> new Subscription(webPush.getEndpoint(), new Subscription.Keys(webPush.getP256dh(), webPush.getAuth())))
                .toList();
        String msg = objectMapper.writeValueAsString(dto);

        subscriptionList.forEach(subscription -> {
            try {
                pushService.send(new Notification(subscription, msg));
            } catch (Exception ignored) {}
        });
    }

}
