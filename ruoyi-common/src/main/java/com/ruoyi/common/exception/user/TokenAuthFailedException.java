package com.ruoyi.common.exception.user;

public class TokenAuthFailedException extends RuntimeException {

    public TokenAuthFailedException(String msg) {
        super(msg);
    }
}
