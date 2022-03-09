package com.ruoyi.common.sms.base;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 手机验证码工具类
 */
public class SmsCodeUtils {
    final static String SMS_CODE_PREFIX = "sms_code_prefix";

    private static RedisTemplate<String,String> redisTemplate;
    @Resource
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成随机4位数字
     * @return
     */
    public static ValidateCode generate() {
        String code = RandomStringUtils.randomNumeric(4);
        return new ValidateCode(code, 60);
    }
    /**
     * 验证码五分钟有效
     */
    public static void sendCode(String mobile, String code) {
        redisTemplate.opsForValue().set(SMS_CODE_PREFIX.concat(":").concat(mobile), code, 5L, TimeUnit.MINUTES);
        System.out.println("向手机"+mobile+"发送短信验证码"+code);
    }

}