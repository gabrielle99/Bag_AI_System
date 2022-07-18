package com.tianci.media.service;

import com.github.pagehelper.PageInfo;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.media.pojo.ImagePojo;
import com.tianci.model.user.pojo.TcUserImageObj;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public ResponseResult uploadImage(MultipartFile multipartFile, String filetag, String bussinessKey, String metadata, Long userId);
    public ResponseResult getHistoryImage(Integer pageNum, Integer pageSize, Long userId);
    public ResponseResult findByPage(Integer pageNum, Integer size, String userId);
}
