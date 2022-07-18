package com.tianci.common.aspect;

import com.tianci.common.annotation.Log;
import com.tianci.common.service.LogService;
import com.tianci.model.log.LogPojo;
import com.tianci.utils.http.HttpContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

@Aspect     // used in transaction
@Component
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    LogService logService;

    @Pointcut("@annotation(com.tianci.common.annotation.Log)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long beginTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis() - beginTime;
        saveLog(proceedingJoinPoint, endTime);
        return result;
    }

    private void saveLog(ProceedingJoinPoint proceedingJoinPoint, long endTime){
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
        LogPojo logPojo = new LogPojo();
        Method method = methodSignature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null){
            logPojo.setOperation(logAnnotation.value());
        }
        String methodName = methodSignature.getName();
        logPojo.setMethod(methodName);
        Parameter[] parameters = method.getParameters();
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuffer argsBuffer = new StringBuffer();
        if (parameters != null && parameters.length > 0){
            for (int i=0; i<args.length; i++){
                if (args[i] != null){
                    argsBuffer.append(parameters[i].getName()+":"+args[i].toString());
                    argsBuffer.append(",");
                }
            }
            String argsTemp = argsBuffer.toString().substring(0, argsBuffer.length()-1);
            logPojo.setParams(argsTemp);
        }

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        logPojo.setIp(request.getRemoteAddr());
        logPojo.setTime(endTime);
        logPojo.setLogCreateTime(new Date());
        logService.save(logPojo);
    }
}
