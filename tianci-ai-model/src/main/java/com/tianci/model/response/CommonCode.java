package com.tianci.model.response;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode{
    INVALID_PARAMS(false,9999,"Invalid Parameters"),
    TEMPLATE_IS_NULL(false,10004,"Cannot find required template."),
    SUCCESS(true,10000,"Success"),
    FAIL(false,11111,"Failure"),
    UNAUTHENTICATED(false,10001,"Login first"),
    PAGE_NOT_EXIST(false,1002,"Page Not Exist"),
    DATA_URL_IS_NONE(false,1003,"Data URL is Empty"),
    UNAUTHORIZED(false,10002,"Unauthorized"),
    SERVER_ERROR(false,99999,"Server Not Response");

    boolean success;
    int code;
    String message;

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }
}
