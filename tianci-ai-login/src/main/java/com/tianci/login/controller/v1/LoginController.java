package com.tianci.login.controller.v1;

import com.tianci.login.service.TcUserLoginService;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.user.pojo.TcUserObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController     // will transfer result into json format
@CrossOrigin
@RequestMapping("api/v1/login")
public class LoginController {
    @Resource
    TcUserLoginService tcUserLoginService;
    @GetMapping("/hello")
    public String test(){
        return "hello";
    }

    // @RequestBody: POST body should be in json format
    @PostMapping("/login_auth")
    public ResponseResult login(@RequestBody TcUserObject tcUserObject){
        System.out.println(tcUserObject);
        return tcUserLoginService.loginAuth(tcUserObject);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody TcUserObject tcUserObject){
        System.out.println(tcUserObject);
        return tcUserLoginService.register(tcUserObject);
    }

}
