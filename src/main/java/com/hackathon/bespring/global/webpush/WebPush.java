package com.hackathon.bespring.global.webpush;

import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebPush extends BaseTimeEntity {

    @Id
    @Column(length = 512)
    private String endpoint;

    @Column
    private String auth;

    @Column
    private String p256dh;

    @Column
    private Long userId;

    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @Builder
    public WebPush(String endpoint, String auth, String p256dh, Long userId, User user) {
        this.endpoint = endpoint;
        this.auth = auth;
        this.p256dh = p256dh;
        this.userId = userId;
        this.user = user;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

}
