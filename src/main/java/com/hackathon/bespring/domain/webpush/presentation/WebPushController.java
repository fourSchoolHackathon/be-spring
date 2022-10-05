package com.hackathon.bespring.domain.webpush.presentation;

import com.hackathon.bespring.domain.webpush.service.WebPushService;
import com.hackathon.bespring.domain.webpush.presentation.dto.request.WebPushSubscribeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("webpush")
@RequiredArgsConstructor
public class WebPushController {

    private final WebPushService webPushService;

    @PostMapping
    public void subscribe(@RequestBody WebPushSubscribeRequest dto) {
        webPushService.subscribe(dto);
    }

    @DeleteMapping
    public void unsubscribe() {
        webPushService.unsubscribe();
    }

}
