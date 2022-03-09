package com.ruoyi.web.controller.sms;

import com.ruoyi.common.SmsCodeUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class SmsController {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 验证码登录
     * @param mobile 用户名
     * @param code 验证码
     * @return
     */
    @PostMapping("/smsLogin")
    public AjaxResult loginSms(String mobile, String code)
    {
        String token = "";

        if (StringUtils.isNull(mobile)) {
            return AjaxResult.error("手机号不能为空~");
        }
        if (StringUtils.isNull(code)) {
            return AjaxResult.error("验证码不能为空~");
        }
        String smsCodeRedisKey = SmsCodeUtils.buildRedisSmsCodeKeyStr(mobile);
        Object o = redisTemplate.opsForValue().get(smsCodeRedisKey);
        if (o != null) {
            String codeInRedis = o.toString();
            if (!codeInRedis.equals(code)){//验证码不匹配
                return AjaxResult.error("验证码输入有误~");
            }
            else if (codeInRedis.equals(code)){//正确
                //清除redis中的验证码
                redisTemplate.delete(smsCodeRedisKey);
                //生成token
                token = UUID.randomUUID().toString().replaceAll("-","");
                //保存token到redis
                redisTemplate.opsForValue().set("client-tokens".concat(":").concat(mobile), token, 10, TimeUnit.SECONDS);
            }
        }
        return AjaxResult.success(token);
    }

    /**
     * 发送手机验证码
     * @param mobile
     * @return
     */
    @GetMapping("/smsCode")
    public AjaxResult sendSmsCode(@RequestParam String mobile) {
        if (StringUtils.isNull(mobile)) {
            return AjaxResult.error("手机号不能为空~");
        }
        String smsCodeRedisKey = SmsCodeUtils.buildRedisSmsCodeKeyStr(mobile);
        String code = SmsCodeUtils.generateSmsCode(4);//4位数字验证码
        //验证码保存到redis，5分钟有效
        redisTemplate.opsForValue().set(smsCodeRedisKey, code, 5, TimeUnit.MINUTES);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);
        return AjaxResult.success(map);
    }

    @GetMapping("/test")
    public AjaxResult test(){
        log.info("你好中国");
        return AjaxResult.success("手机号登录token认证通过");
    }


}
