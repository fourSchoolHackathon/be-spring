package com.hackathon.bespring.global.security.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class InvalidToken extends CustomException {

    public static final CustomException EXCEPTION =
            new InvalidToken();

    private InvalidToken() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
