package com.ruoyi.common.exception.user;

/**
 * 自定义token异常
 */
public class TokenException extends RuntimeException {

    public TokenException(String msg) {
        super(msg);
    }

}
