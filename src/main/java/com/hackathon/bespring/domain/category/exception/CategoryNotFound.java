package com.hackathon.bespring.domain.category.exception;

import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;

public class CategoryNotFound extends CustomException {

    public static final CustomException EXCEPTION =
            new CategoryNotFound();

    private CategoryNotFound() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
