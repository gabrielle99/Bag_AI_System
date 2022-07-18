package com.tianci.model.common.enums;

public enum AppHttpCodeEnum {
    /**
     * Invoke private constructor, create static AppHttpCodeEnum object
     */
    // success 0
    SUCCESS(0,"OPERATION SUCCESS"),
    // login 1~50
    NEED_LOGIN(1,"LOGIN FIRST"),
    LOGIN_PASSWORD_ERROR(2,"Password Incorrect"),
    // TOKEN50~100
    TOKEN_INVALID(50,"INVALID TOKEN"),
    TOKEN_EXPIRE(51,"TOKEN EXPIRE"),
    TOKEN_REQUIRE(52,"TOKEN NECESSARY"),
    // SIGN check 100~120
    SIGN_INVALID(100,"INVALID SIGN"),
    SIG_TIMEOUT(101,"SIGN EXPIRE"),
    // param false 500~1000
    PARAM_REQUIRE(500,"LACK PARAM"),
    PARAM_INVALID(501,"INVALID PARAM"),
    PARAM_IMAGE_FORMAT_ERROR(502,"FALSE IMAGE FORMAT"),
    SERVER_ERROR(503,"INTERNAL SERVER ERROR"),
    // DATA ERROR 1000~2000
    DATA_EXIST(1000,"DATA ALREADY EXIST"),
    AP_USER_DATA_NOT_EXIST(1001,"ApUser DATA NOT FOUND"),
    DATA_NOT_EXIST(1002,"DATA NOT FOUND"),
    // DATA ERROR 3000~3500
    NO_OPERATOR_AUTH(3000,"PERMISSION DENIED");

    int code;
    String message;

    AppHttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
