package com.hackathon.bespring.infrastructure.feign;

import com.hackathon.bespring.infrastructure.feign.dto.request.MatchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MatchServer", url = "https://2022hackathon.bssm.kro.kr/match")
public interface MatchServer {

    @PostMapping
    void match(@RequestBody MatchRequest request);
}
