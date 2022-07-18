package com.tianci.api.media;

import com.tianci.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
@Api(value = "file controller")
public interface MediaControllerApi {
    @ApiOperation("upload image and save to fastdfs server")
    public ResponseResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata, String token);
}
