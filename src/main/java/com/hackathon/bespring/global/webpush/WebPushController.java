package com.hackathon.bespring.global.webpush;

import com.hackathon.bespring.global.webpush.dto.request.WebPushSendDto;
import com.hackathon.bespring.global.webpush.dto.request.WebPushSubscribeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("webpush")
@RequiredArgsConstructor
public class WebPushController {

    private final WebPushService webPushService;

    @PostMapping
    public void subscribe(@RequestBody WebPushSubscribeDto dto) {
        webPushService.subscribe(dto);
    }

    @PostMapping("send")
    public void send(@RequestBody WebPushSendDto dto) {
        webPushService.send(dto);
    }

}
