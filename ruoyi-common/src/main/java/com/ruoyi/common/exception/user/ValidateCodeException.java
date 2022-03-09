package com.ruoyi.common.exception.user;


/**
 * 自定义验证码异常类
 */
public class ValidateCodeException extends RuntimeException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}