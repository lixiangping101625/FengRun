package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;

/**
 * @author Lixiangping
 * @createTime 2022年03月10日 13:07
 * @decription: 短信service
 */
public interface ISmsService {

    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    String sendVerifyCode(String mobile);

    /**
     * 移除redis中的短信验证码
     * @param smsVerifyCodeKey
     * @return
     */
    boolean verifyCodeExpired(String smsVerifyCodeKey);

}
