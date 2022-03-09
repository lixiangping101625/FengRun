package com.ruoyi.framework.interceptor;

import com.ruoyi.common.exception.user.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        response.setHeader("contentType", "text/js; charset=utf-8");

        boolean flag = false;
        String notice = "";
        // Token 验证
        String token = request.getHeader("Authorization");

//        redisTemplate.opsForValue().get("client-tokens".concat(":").concat())
        if (StringUtils.isEmpty(token) || !token.equals("tokenTest")){
            throw new ValidateCodeException("认证失败");
        }
        return true;
    }
}