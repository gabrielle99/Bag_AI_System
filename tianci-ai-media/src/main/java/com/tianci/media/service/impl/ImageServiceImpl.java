package com.tianci.media.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianci.common.exception.ExceptionCast;
import com.tianci.common.fastdfs.FastdfsClient;
import com.tianci.media.dao.MediaSystemRepository;
import com.tianci.media.service.ImageService;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.common.enums.AppHttpCodeEnum;
import com.tianci.model.mappers.app.TcUserImageMapper;
import com.tianci.model.mappers.app.TcUserMapper;
import com.tianci.model.media.pojo.ImagePojo;
import com.tianci.model.response.CommonCode;
import com.tianci.model.user.pojo.TcUserImageObj;
import com.tianci.model.user.pojo.TcUserObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Resource
    FastdfsClient fastdfsClient;
    @Resource
    MediaSystemRepository mediaSystemRepository;
    @Resource
    TcUserMapper tcUserMapper;
    @Resource
    TcUserImageMapper tcUserImageMapper;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public ResponseResult uploadImage(MultipartFile multipartFile, String filetag, String bussinessKey, String metadata, Long userId) {
        if (null == multipartFile){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        TcUserObject user = new TcUserObject();
        if (userId != null){
            user.setId(userId);
        } else {
            user.setId(2L);
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String extName = originalFileName.substring(originalFileName.indexOf(".")+1);
        if (!extName.matches("(png|jpg|jpeg)")){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_IMAGE_FORMAT_ERROR);
        }
        String fileId="";
        try{
            fileId = fastdfsClient.uploadFile(multipartFile.getBytes(), extName);
            System.out.println("fastdfs fileid: "+fileId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImagePojo imagePojo = new ImagePojo();
        imagePojo.setFileId(fileId);
        imagePojo.setFilePath(fileId);
        imagePojo.setFiletag(filetag);
        imagePojo.setBusinesskey(bussinessKey);
        imagePojo.setFileName(multipartFile.getOriginalFilename());
        imagePojo.setFileType(multipartFile.getContentType());
        imagePojo.setUserId(String.valueOf(user.getId()));
        if (StringUtils.isNotEmpty(metadata)){
            Map map = JSON.parseObject(metadata, Map.class);
            imagePojo.setMetadata(map);
        }
        mediaSystemRepository.save(imagePojo);

        TcUserImageObj userImageObj = new TcUserImageObj();
        userImageObj.setImageId(fileId);
        userImageObj.setUserId(user.getId());
        userImageObj.setCreatedTime(new Date());
        userImageObj.setStatus(true);
        String result = (String)redisTemplate.boundValueOps(imagePojo.getFileName()).get();
        if (result == null){
            System.out.println("send image recognize msg");
            result = this.sendImageRecognizeMsg(fileId);
        }
//        String result = this.sendImageRecognizeMsg(fileId);
        userImageObj.setPredictedResult(result);
        int savedResult = tcUserImageMapper.saveUserImage(userImageObj);
        System.out.println("saved result: "+savedResult);

        redisTemplate.opsForValue().set(imagePojo.getFileName(), result);

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setData(result);
        return responseResult;
    }

    public String sendImageRecognizeMsg(String fileId){
        Optional<ImagePojo> optional = mediaSystemRepository.findById(fileId);
        if (!optional.isPresent()){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        try{
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://10.0.0.114:5000/ai?img_url="+fileId).build();
            try(Response response=okHttpClient.newCall(request).execute()) {
                // ResponseBody can only get once
                ResponseBody responseBody = response.body();
                if (response.isSuccessful()){
                    String data = responseBody.string();
                    log.info("success{}",responseBody==null?"":data);
                    ImagePojo imagePojo = optional.get();
                    imagePojo.setPredictedResult(data);
                    mediaSystemRepository.save(imagePojo);
                    return data;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ResponseResult getHistoryImage(Integer pageNum, Integer pageSize, Long userId){
        TcUserObject userObject = new TcUserObject();
//        userObject.setId(2L);
        if (userId != null){
            userObject.setId(userId);
        } else {
            userObject.setId(2L);
        }
        System.out.println("userId in service = "+userId);
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<ImagePojo> page = mediaSystemRepository.findByUserIdEquals(userObject.getId().toString(),pageable);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setData(page);
        return responseResult;
    }

    @Override
    public ResponseResult findByPage(Integer pageNum, Integer size, String userId){
        PageHelper.startPage(pageNum, size);
        List<TcUserImageObj> list = tcUserImageMapper.selectAllByUserId(Long.valueOf(userId));
        PageInfo<TcUserImageObj> info = new PageInfo<>(list);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setData(info);
        return responseResult;
    }
}
