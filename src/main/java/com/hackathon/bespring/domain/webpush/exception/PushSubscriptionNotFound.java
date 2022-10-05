package com.hackathon.bespring.domain.webpush.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class PushSubscriptionNotFound extends CustomException {

    public static final CustomException EXCEPTION =
            new PushSubscriptionNotFound();

    private PushSubscriptionNotFound() {
        super(ErrorCode.PUSH_SUBSCRIPTION_NOT_FOUND);
    }
}
