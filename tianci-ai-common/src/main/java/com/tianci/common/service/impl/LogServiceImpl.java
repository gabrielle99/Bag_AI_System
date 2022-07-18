package com.tianci.common.service.impl;

import com.tianci.common.service.LogService;
import com.tianci.model.log.LogPojo;
import com.tianci.model.mappers.log.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    LogMapper logMapper;
    @Override
    public void save(LogPojo logPojo) {
        System.out.println("logPojo =>"+logPojo);
        logMapper.save(logPojo);
    }
}
