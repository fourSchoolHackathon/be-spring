package com.hackathon.bespring.domain.user.presentation.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class UserExists extends CustomException {

    public static final CustomException EXCEPTION =
            new UserExists();

    private UserExists() {
        super(ErrorCode.USER_EXISTS);
    }
}
