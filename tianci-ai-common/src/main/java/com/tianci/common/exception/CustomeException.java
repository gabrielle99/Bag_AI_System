package com.tianci.common.exception;

import com.tianci.model.response.ResultCode;

public class CustomeException extends RuntimeException{
    private ResultCode resultCode;
    public CustomeException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
