package com.ruoyi.web.controller.sms;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@Slf4j
public class SmsController {

    @Resource
    private ISmsService smsService;

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
        String verifyCode = smsService.sendVerifyCode(mobile);
        if (StringUtils.isEmpty(verifyCode)){
            return AjaxResult.error("发送手机验证码失败~");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("verifyCode", verifyCode);
        return AjaxResult.success(map);
    }

}
