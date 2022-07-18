package com.tianci.media.controller;

import com.github.pagehelper.PageInfo;
import com.tianci.api.media.MediaControllerApi;
import com.tianci.common.annotation.Log;
import com.tianci.media.service.ImageService;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.media.pojo.ImagePojo;
import com.tianci.model.user.pojo.TcUserImageObj;
import com.tianci.utils.jwt.AppJwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/media")
public class MediaController implements MediaControllerApi {
    @Resource
    ImageService imageService;
    @GetMapping("/test")
    @Log("test log service")
    public ResponseResult test(){
        return ResponseResult.okResult(200, "Test Success");
    }

    // value for params with @RequestParam annotation cannot be null in default; otherwise, need to set its required = false
    @Override
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile multipartFile,String filetag, String bussinessKey, String metadata, String token){
        Long userId = 2l;
        if (token != null){
            Claims claims = AppJwtUtil.getClaimsBody(token);
            Object id = claims.get("id");
            userId = Long.valueOf((Integer) id);
        }
        ResponseResult responseResult = imageService.uploadImage(multipartFile, filetag, bussinessKey, metadata, userId);
        System.out.println("Upload image");
        return responseResult;
    }

    @PostMapping("/history")
    public ResponseResult getSearchHistory(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, Long userId){
        return imageService.getHistoryImage(pageNum, pageSize, userId);
    }

    @GetMapping("/list")
    public ResponseResult findByPage(@RequestParam("pageNum")Integer pageNum, @RequestParam("size")Integer size, String userId){
        return imageService.findByPage(pageNum, size, userId);
    }
}
