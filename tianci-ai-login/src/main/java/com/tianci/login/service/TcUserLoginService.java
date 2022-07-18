package com.tianci.login.service;

import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.user.pojo.TcUserObject;
import org.springframework.stereotype.Service;

public interface TcUserLoginService {
    ResponseResult loginAuth(TcUserObject tcUserObject);
    ResponseResult register(TcUserObject tcUserObject);
}
