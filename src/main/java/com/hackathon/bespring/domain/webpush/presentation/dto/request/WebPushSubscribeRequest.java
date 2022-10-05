package com.hackathon.bespring.domain.webpush.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
<<<<<<< Updated upstream
=======
import javax.validation.constraints.Size;
>>>>>>> Stashed changes

@Getter
@NoArgsConstructor
public class WebPushSubscribeRequest {

    @NotBlank
    private String endpoint;

    @NotBlank
<<<<<<< Updated upstream
    private String auth;

    @NotBlank
=======
    @Size(min = 22, max = 22)
    private String auth;

    @NotBlank
    @Size(min = 87, max = 87)
>>>>>>> Stashed changes
    private String p256dh;

}
