package com.tianci.model.mappers.app;

import com.tianci.model.user.pojo.TcUserImageObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcUserImageMapper {
    int saveUserImage(TcUserImageObj userImageObj);
    List<TcUserImageObj> selectAllByUserId(@Param("user_id")Long userId);
}
