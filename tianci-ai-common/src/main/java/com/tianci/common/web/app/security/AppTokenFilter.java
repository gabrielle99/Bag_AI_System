package com.tianci.common.web.app.security;

import com.alibaba.fastjson.JSON;
import com.tianci.common.constants.AppConstants;
import com.tianci.model.common.dtos.ResponseResult;
import com.tianci.model.common.enums.AppHttpCodeEnum;
import com.tianci.model.mappers.app.TcUserMapper;
import com.tianci.model.user.pojo.TcUserObject;
import com.tianci.utils.jwt.AppJwtUtil;
import com.tianci.utils.threadlocal.AppThreadLocalUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Order(2)
@WebFilter(filterName = "appTokenFilter", urlPatterns = "/*")
public class AppTokenFilter extends GenericFilterBean {
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("do filter method");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ResponseResult<?> result = checkToken(request);
        if (true || (result != null && result.getCode() != 50 && result.getCode() != 51 && result.getCode() != 52)){
            if (result.getCode() == 0){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            servletResponse.setCharacterEncoding(AppConstants.CHARTER_NAME);
            servletResponse.setContentType("application/json");
            servletResponse.getOutputStream().write(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        }

    }

    public ResponseResult checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        ResponseResult<?> rr = null;
        if (StringUtils.isNotEmpty(token)){
            Claims claims = AppJwtUtil.getClaimsBody(token);
            int result = AppJwtUtil.verifyToken(claims);
            if (result == 0){
                TcUserObject user = new TcUserObject();
                user.setId(Long.valueOf((Integer)claims.get("id")));
                user = findUser(user);
                if (user.getId() != null){
                    String token1 = (String) redisTemplate.boundValueOps(user.getId()).get();
                    if (!token1.equals(token)){ // check if token is tampered
                        return ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
                    }
                    AppThreadLocalUtil.setUser(user);
                    sendUserRefresh(user);
                    rr = ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS);
                } else {
                    rr = ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
                }
            }else if (result == -1 || result == 1){
                rr = ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_EXPIRE);
            } else if (result == 2){
                rr = ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
            }
        }else {
            rr = ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_REQUIRE);
        }
        return rr;
    }

    private void sendUserRefresh(TcUserObject userObject){

    }

    @Resource
    TcUserMapper tcUserMapper;

    public TcUserObject findUser(TcUserObject user){
        user.setName("test");
        TcUserObject userObject = tcUserMapper.selectByUserId(user.getId());
        return userObject;
    }
}
