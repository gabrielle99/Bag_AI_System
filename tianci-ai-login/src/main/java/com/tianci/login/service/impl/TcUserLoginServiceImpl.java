package com.tianci.login.service.impl;

import com.mysql.jdbc.StringUtils;
import com.tianci.login.service.TcUserLoginService;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.common.enums.AppHttpCodeEnum;
import com.tianci.model.mappers.app.TcUserMapper;
import com.tianci.model.user.pojo.TcUserObject;
import com.tianci.utils.jwt.AppJwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Without this in the implementation class, will have error message: No qualifying bean of type 'com.tianci.login.service.TcUserLoginService' available
// Even though put @Service in interface, will prompt the same error msg
@Service
public class TcUserLoginServiceImpl implements TcUserLoginService {
    @Resource
    TcUserMapper tcUserMapper;
    @Resource
    RedisTemplate redisTemplate;
    @Override
    public ResponseResult loginAuth(TcUserObject userObject) {
        if (null == userObject || StringUtils.isNullOrEmpty(userObject.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        TcUserObject user = tcUserMapper.login(userObject.getName(), userObject.getPassword());
        if (user == null){
            TcUserObject userObject1 = tcUserMapper.selectByName(userObject.getName());
            if (userObject1 == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
            }else if (!userObject1.getPassword().equals(userObject.getPassword())){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
            }
        }
        Map<String, Object> data = new HashMap<>();
//        String token = AppJwtUtil.getToken(userObject);       // userObject do not have id
        String token = AppJwtUtil.getToken(user);
        data.put("token", token);
        data.put("user", user);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        redisTemplate.opsForValue().setBit("login", day, true);
        redisTemplate.opsForValue().set(user.getId(), token);
//        return ResponseResult.okResult(200,"Login Success");
        return ResponseResult.okResult(data);
    }

    @Override
    public ResponseResult register(TcUserObject tcUserObject) {
        if (null == tcUserObject || StringUtils.isNullOrEmpty(tcUserObject.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        try{
            tcUserObject.setCreatedTime(new Date());
            tcUserMapper.register(tcUserObject);
            return ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS);
        }catch (Exception e){
            System.out.println("error:"+e.getMessage());
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
    }


}
