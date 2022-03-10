package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;

/**
 * @author Lixiangping
 * @createTime 2022年03月10日 13:30
 * @decription: 短信验证码注册（登录）
 */
public interface ISmsLoginService {

    /**
     * 验证码（注册）登录
     * @param mobile
     * @param verifyCode
     * @return
     */
    AjaxResult loginMobile(String mobile, String verifyCode);

}
