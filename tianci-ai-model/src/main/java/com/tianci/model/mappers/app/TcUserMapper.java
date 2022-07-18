package com.tianci.model.mappers.app;
import com.tianci.model.user.pojo.TcUserObject;

public interface TcUserMapper {
    TcUserObject selectByUserId(Long id);
    TcUserObject login(String name, String password);
    TcUserObject selectByName(String name);
    int register(TcUserObject userObject);
}
