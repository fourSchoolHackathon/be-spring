package com.hackathon.bespring.global.webpush;

import com.hackathon.bespring.global.UserUtil;
import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;
import com.hackathon.bespring.global.webpush.dto.request.WebPushSendDto;
import com.hackathon.bespring.global.webpush.dto.request.WebPushSubscribeDto;
import com.hackathon.bespring.global.webpush.repository.WebPushRepository;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class WebPushService {

    private final UserUtil userUtil;
    private final WebPushRepository webpushRepository;
    private PushService pushService;

    @Value("${WebPushPublicKey}")
    private String publicKey;
    @Value("${WebPushPrivateKey}")
    private String privateKey;

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public void subscribe(WebPushSubscribeDto dto) {
        WebPush webPush = webpushRepository.findByUserId(userUtil.getCurrentUser().getId()).orElseGet(
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

    public void send(WebPushSendDto dto) {
        WebPush webPush = webpushRepository.findByUserId(userUtil.getCurrentUser().getId()).orElseThrow(
                () -> {throw new CustomException(ErrorCode.PUSH_SUBSCRIPTION_NOT_FOUND);}
        );
        sendNotification(webPush, dto.getMsg());
    }

    private void sendNotification(WebPush webPush, String msg) {
        Subscription subscription = new Subscription(webPush.getEndpoint(), new Subscription.Keys(webPush.getP256dh(), webPush.getAuth()));
        try {
            pushService.send(new Notification(subscription, msg));
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
