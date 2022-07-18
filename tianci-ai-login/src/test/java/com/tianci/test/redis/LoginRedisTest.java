package com.tianci.test.redis;

import com.tianci.login.LoginJarApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest(classes = LoginJarApplication.class)
@RunWith(SpringRunner.class)
public class LoginRedisTest {

    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void setBit(){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        int day = calendar.get(Calendar.DAY_OF_YEAR);
//        System.out.println("day:"+day);
////        for (int i = 1; i<366; i++){
////            System.out.print(redisTemplate.opsForValue().getBit("test", day)+" ");
////        }
//        redisTemplate.opsForValue().setBit("test", day, true);
//        System.out.println("redis:");
//        for (int i = 1; i<366; i++){
//            System.out.print(redisTemplate.opsForValue().getBit("test", i)+" ");
//        }
        System.out.println("login:"+redisTemplate.opsForValue().getBit("login", 104));
        System.out.println("login:"+redisTemplate.opsForValue().getBit("login", 103));
    }
}
