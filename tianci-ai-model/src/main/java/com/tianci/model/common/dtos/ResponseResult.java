package com.tianci.model.common.dtos;

import com.tianci.model.common.enums.AppHttpCodeEnum;
import com.tianci.model.response.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String host;
    private String errorMessage;
    private T data;
    boolean success = false;
    private ResultCode resultCode;

    public ResponseResult(){
        this.code = 200;
    }

    public ResponseResult(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String errorMessage, T data) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public ResponseResult(Integer code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public static ResponseResult errorResult(Integer code,String errorMessage){
        ResponseResult responseResult=new ResponseResult();
        return responseResult.error(code,errorMessage);
    }

    public static ResponseResult errorResult(AppHttpCodeEnum appHttpCodeEnum){
        ResponseResult responseResult=new ResponseResult();
        return responseResult.error(appHttpCodeEnum.getCode(),appHttpCodeEnum.getMessage());
    }

    public static ResponseResult errorResult(AppHttpCodeEnum appHttpCodeEnum,String errorMessage){
        return setAppHttpCodeEnum(appHttpCodeEnum,errorMessage);
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
        return this;
    }

    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        if (null!=data){
            result.setData(data);
        }
        return result;
    }

    public ResponseResult<?> ok(Integer code,T data,String msg) {
        this.code = code;
        this.errorMessage = msg;
        this.data=data;
        return this;
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums){
        return okResult(enums.getCode(),enums.getMessage());
    }
    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String errorMessage){
        return okResult(enums.getCode(),errorMessage);
    }
}
