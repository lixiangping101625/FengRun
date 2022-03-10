package com.ruoyi.web.controller.auth;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISmsLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Lixiangping
 * @createTime 2022年03月10日 13:04
 * @decription: 短信登录
 */
@RestController
public class LoginController {

    @Resource
    private ISmsLoginService smsLoginService;

    /**
     * 验证码登录
     * @param mobile 用户名
     * @param code 验证码
     * @return
     */
    @PostMapping("/smsLogin")
    public AjaxResult loginSms(String mobile, String code)
    {
        if (StringUtils.isNull(mobile)) {
            return AjaxResult.error("手机号不能为空~");
        }
        if (StringUtils.isNull(code)) {
            return AjaxResult.error("验证码不能为空~");
        }
        return smsLoginService.loginMobile(mobile, code);
    }

}
