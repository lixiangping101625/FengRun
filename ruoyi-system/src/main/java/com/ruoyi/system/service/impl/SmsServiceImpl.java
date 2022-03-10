package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.sms.SmsCodeUtils;
import com.ruoyi.common.utils.sms.SmsConstant;
import com.ruoyi.system.service.ISmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Lixiangping
 * @createTime 2022年03月10日 13:08
 * @decription: 短信service实现类
 */
@Component
public class SmsServiceImpl implements ISmsService {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${sms.verifyCode.len}")
    private Integer verifyCodeLen;
    @Value("${sms.verifyCode.expire}")
    private Integer expire;

    @Override
    public String sendVerifyCode(String mobile) {
        String code = null;
        try {
            code = SmsCodeUtils.generateSmsCode(verifyCodeLen);//4位数字验证码
            String smsCodeRedisKey = SmsCodeUtils.buildRedisSmsCodeKeyStr(mobile);
            redisTemplate.opsForValue().set(smsCodeRedisKey, code, expire, TimeUnit.SECONDS);

            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    @Override
    public boolean verifyCodeExpired(String smsVerifyCodeKey) {
        return false;
    }
}
