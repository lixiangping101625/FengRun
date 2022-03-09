package com.ruoyi.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 手机验证码成功登录响应VO
 */
@Data
public class MobileLoginTokenVO implements Serializable {

    private String token;
    private Long userId;
    private String loginTime;
    private String expiredTime;

}
