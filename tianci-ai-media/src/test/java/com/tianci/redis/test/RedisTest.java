package com.tianci.redis.test;

import com.tianci.media.MediaJarApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest(classes = MediaJarApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Resource
    RedisTemplate redisTemplate;
    @Test
    public void testRedis(){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("pet", "dog");
    }


    @Test
    public void testRedisGet(){
//        String str1 = (String)redisTemplate.boundValueOps("pet").get();
        String str1 = (String)redisTemplate.boundValueOps("pet2").get();
        System.out.println(str1);
    }

    @Test
    public void setBit(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println("day:"+day);
//        for (int i = 1; i<366; i++){
//            System.out.print(redisTemplate.opsForValue().getBit("test", day)+" ");
//        }
        redisTemplate.opsForValue().setBit("test", day, true);
        System.out.println("redis:");
        for (int i = 1; i<366; i++){
            System.out.print(redisTemplate.opsForValue().getBit("test", i)+" ");
        }
        System.out.println();
        System.out.println("get:"+redisTemplate.opsForValue().getBit("test", 104));
    }
}
