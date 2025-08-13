package com.quanxiaoha.xiaohashu.auth.constant;

public class RedisKeyConstants {

    private static final String VERIFICATION_CODE_KEY_PREFIX = "";

    public static String buildVerificationCodeKey(String phone){
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }

}
