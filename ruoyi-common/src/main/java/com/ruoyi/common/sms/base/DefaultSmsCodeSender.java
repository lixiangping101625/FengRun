package com.ruoyi.common.sms.base;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
public class DefaultSmsCodeSender {

    final static String SMS_CODE_PREFIX = "sms_code_prefix";//手机验证码前缀

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    public void send(String mobile, String code) {
        /**
         * 验证码五分钟有效
         */
        redisTemplate.opsForValue().set(SMS_CODE_PREFIX.concat(":").concat(mobile), code, 5L, TimeUnit.MINUTES);
        System.out.println("向手机"+mobile+"发送短信验证码"+code);
    }
}