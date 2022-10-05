package com.hackathon.bespring.global.security.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class ExpiredToken extends CustomException {

    public static final CustomException EXCEPTION =
            new ExpiredToken();

    private ExpiredToken() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
