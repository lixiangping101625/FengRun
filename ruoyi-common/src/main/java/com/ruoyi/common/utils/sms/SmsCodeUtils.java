package com.ruoyi.common.utils.sms;

import java.util.Random;

/**
 * @author 李向平
 * 短信验证码工具类
 */
public class SmsCodeUtils {

    //手机验证码前缀
    final static String MOBILE_VERIFY_CODE = "mobile_sms_code";

    /**
     * 生成手机验证码
     * @param codeLen
     * @return
     */
    public static String generateSmsCode(int codeLen) {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < codeLen; i++) {
            code = code.concat(random.nextInt(10)+"");
        }
        return code;
    }

    /**
     * 拼接手机验证码在redis 中的key
     * @param mobile 手机号
     * @return
     */
    public static String buildRedisSmsCodeKeyStr(String mobile) {
        return MOBILE_VERIFY_CODE.concat(":").concat(mobile);
    }

}
