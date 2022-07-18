package com.tianci.model.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LogPojo {
    private Long id;

    private Long userId;

    private String username;

    private String operation;

    private Long time;

    private String method;

    private String params;

    private String ip;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logCreateTime;
}
