package com.hackathon.bespring.global.security.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class UserNotFound extends CustomException {

    public static final CustomException EXCEPTION =
            new UserNotFound();

    private UserNotFound() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
