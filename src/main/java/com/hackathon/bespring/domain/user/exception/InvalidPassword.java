package com.hackathon.bespring.domain.user.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class InvalidPassword extends CustomException {

    public static final CustomException EXCEPTION =
            new InvalidPassword();

    private InvalidPassword() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
