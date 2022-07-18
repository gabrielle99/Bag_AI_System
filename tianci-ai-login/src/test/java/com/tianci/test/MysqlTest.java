package com.tianci.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianci.login.LoginJarApplication;
import com.tianci.login.service.TcUserLoginService;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.common.enums.AppHttpCodeEnum;
import com.tianci.model.mappers.app.TcUserImageMapper;
import com.tianci.model.mappers.app.TcUserMapper;
import com.tianci.model.user.pojo.TcUserImageObj;
import com.tianci.model.user.pojo.TcUserObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = LoginJarApplication.class)
@RunWith(SpringRunner.class)
public class MysqlTest {
    @Resource
    SqlSessionFactoryBean sqlSessionFactoryBean;
    @Resource
    DataSource dataSource;
    @Resource
    TcUserMapper tcUserMapper;
    @Resource
    TcUserLoginService tcUserLoginService;
    @Resource
    TcUserImageMapper tcUserImageMapper;

    @Test
    public void testMysqlConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        long id = 2;
        System.out.println(tcUserMapper.selectByUserId(id));
    }

    @Test
    public void testLogin(){
        TcUserObject userObject = new TcUserObject();
        userObject.setName("cc");
        userObject.setPassword("123");
        ResponseResult responseResult = tcUserLoginService.loginAuth(userObject);
        System.out.println(responseResult.getCode()+":"+responseResult.getErrorMessage());
    }

    @Test
    public void testLogin2(){
        TcUserObject userObject = new TcUserObject();
        userObject.setName("cc");
        userObject.setPassword("111");
        System.out.println(tcUserMapper.login(userObject.getName(), userObject.getPassword()));
    }


    @Test
    public void testSelectName(){
        TcUserObject userObject = new TcUserObject();
        userObject.setName("cc");
        userObject.setPassword("111");
        TcUserObject user = tcUserMapper.selectByName(userObject.getName());
        System.out.println(user);
        if (!user.getPassword().equals(userObject.getPassword())){
            System.out.println("Incorrect password");
        }
    }

    @Test
    public void testImageMapper(){
        TcUserImageObj tcUserImageObj = new TcUserImageObj();
        tcUserImageObj.setUserId(1l);
        tcUserImageObj.setImageId("2");
        tcUserImageObj.setCreatedTime(new Date());
        tcUserImageObj.setFlag(1);
        tcUserImageMapper.saveUserImage(tcUserImageObj);
        tcUserImageObj.setUserId(1l);
        tcUserImageObj.setImageId("3");
        tcUserImageObj.setCreatedTime(new Date());
        tcUserImageObj.setFlag(0);
        tcUserImageObj.setPredictedResult("willow tote");
        tcUserImageMapper.saveUserImage(tcUserImageObj);
        System.out.println(tcUserImageMapper.selectAllByUserId(1l));
    }

    @Test
    public void testSelectImage(){
        String userId = "2";
        System.out.println("select result: "+tcUserImageMapper.selectAllByUserId(Long.valueOf(userId)));
    }

    @Test
    public void testRegister(){
        TcUserObject userObject = new TcUserObject();
//        userObject.setId(1L);
        userObject.setName("tianci");
        userObject.setPassword("000000");
        userObject.setPhone("111-111-111");
        userObject.setLoginTime(new Date());
        userObject.setCreatedTime(new Date());
        int saveResult = tcUserMapper.register(userObject);
        System.out.println("saveResult = "+saveResult);
    }

    @Test
    public void testRegisterService(){
        TcUserObject userObject = new TcUserObject();
        userObject.setName("user1");
        userObject.setPassword("11111");
        ResponseResult responseResult = tcUserLoginService.register(userObject);
        System.out.println("response result: "+responseResult);
    }

//    @Test
//    public void testSelectInReverse(){
//        System.out.println(tcUserImageMapper.selectAllReverse());
//    }

    @Test
    public void testSelect(){
        Integer pageNum = 1;
        Integer pageSize = 6;
        Long userId = 2l;
//        String userId = "2";
        System.out.println(Long.valueOf(userId));
        PageHelper.startPage(pageNum, pageSize);
        List<TcUserImageObj> list = tcUserImageMapper.selectAllByUserId(userId);
//        List<TcUserImageObj> list = tcUserImageMapper.selectAllByUserId(Long.valueOf(userId));
        System.out.println("list:"+list);
        PageInfo<TcUserImageObj> info = new PageInfo<>(list);
        System.out.println("info:"+info);
    }
}
