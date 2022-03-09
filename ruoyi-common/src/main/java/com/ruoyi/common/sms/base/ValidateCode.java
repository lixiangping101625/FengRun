package com.ruoyi.common.sms.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ValidateCode {
    private String code;//验证码
    private LocalDateTime expireTime;//过期时间

    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime){
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}