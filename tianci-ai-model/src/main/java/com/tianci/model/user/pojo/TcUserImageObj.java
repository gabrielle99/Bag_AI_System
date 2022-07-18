package com.tianci.model.user.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class TcUserImageObj {
    private Long id;
    private Long userId;
    private String imageId;
    private Date createdTime;
    private boolean status;
    private int flag;
    private String predictedResult;
}
