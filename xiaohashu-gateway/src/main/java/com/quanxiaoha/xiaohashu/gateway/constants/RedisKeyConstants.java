package com.quanxiaoha.xiaohashu.gateway.constants;

public class RedisKeyConstants {

    private static final String USER_ROEL_KEY_PREFIX = "user:role:";

    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     *
     * @param userId
     * @return
     */
    public static String buildUserRoleKey(Long userId) {
        return USER_ROEL_KEY_PREFIX + userId;
    }

    /**
     *
     * @param roleKey
     * @return
     */
    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }

}
