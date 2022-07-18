package com.tianci.common.exception;

import com.tianci.model.response.ResultCode;

public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomeException(resultCode);
    }
}
