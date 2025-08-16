package com.quanxiaoha.xiaohashu.auth.constant;

public class RedisKeyConstants {

    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code_key_prefix:";

    public  static final String XIAOHASHU_ID_GENERATOR_KEY = "xiaohashu_id_generator";

    private static final String USER_ROLE_KEY_PREFIX = "user:role:";

    // 角色对应权限集合的 KEY 前缀
    private static final String ROLE_PERMISSION_KEY_PREFIX = "role:permissions:";

    public static String buildVerificationCodeKey(String phone){
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }

    public static String buildUserRoleKey(Long userId){
        return USER_ROLE_KEY_PREFIX + userId;
    }

    public static String buildRolePermissionsKey(String roleKey){
        return ROLE_PERMISSION_KEY_PREFIX+roleKey;
    }
}
