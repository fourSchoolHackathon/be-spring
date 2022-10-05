package com.hackathon.bespring.global.webpush;

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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.security.Security;

@Service
@RequiredArgsConstructor
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

}
