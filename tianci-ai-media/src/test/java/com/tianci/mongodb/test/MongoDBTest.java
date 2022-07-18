package com.tianci.mongodb.test;

import com.alibaba.fastjson.JSON;
import com.tianci.media.MediaJarApplication;
import com.tianci.media.dao.MediaSystemRepository;
import com.tianci.model.media.pojo.ImagePojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MediaJarApplication.class)
@RunWith(SpringRunner.class)
public class MongoDBTest {
    @Resource
    private MediaSystemRepository mediaSystemRepository;
    @Test
    public void mongodb_test1(){
        ImagePojo imagePojo = new ImagePojo();
        imagePojo.setFileId("3");
        imagePojo.setFileName("ccc1.txt");
        imagePojo.setFileType("txt");
//        mediaSystemRepository.insert(imagePojo);
        mediaSystemRepository.save(imagePojo);
    }

    @Test
    public void test_json_parser(){
        String str1 = "{\"id\":1,\"name\":\"cc\"}";
        Map map = JSON.parseObject(str1,Map.class);
        System.out.println(map);
    }

    @Test
    public void updateTable(){
        List<ImagePojo> imagePojoList = mediaSystemRepository.findAll();
        for (ImagePojo imagePojo:imagePojoList){
            imagePojo.setUserId("2");
//            mediaSystemRepository.save(imagePojo);
        }
        mediaSystemRepository.saveAll(imagePojoList);
    }

    @Test
    public void selectAllImage(){
        List<ImagePojo> imagePojoList = mediaSystemRepository.findAll();
        for (ImagePojo imagePojo:imagePojoList){
//            System.out.println("info:" +imagePojo.getUserId()+imagePojo.getPredictedResult());
            imagePojo.setUserId("2");
        }
        mediaSystemRepository.saveAll(imagePojoList);
    }
}
