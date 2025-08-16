package com.quanxiaoha.xiaohashu.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.quanxiaoha.xiaohashu.gateway.constants.RedisKeyConstants;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    ObjectMapper objectMapper;

    /**
     *
     * @param loginId  账号id
     * @param loginType 账号类型
     * @return
     */
    @SneakyThrows
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此用户ID拥有的权限列表
        log.info("## 获取用户权限列表, loginId: {}", loginId);
        String userRolesKey = RedisKeyConstants.buildUserRoleKey(Long.valueOf(loginId.toString()));
        String userRolesValue = redisTemplate.opsForValue().get(userRolesKey);
        if (StringUtils.isBlank(userRolesValue)) {
            return null;
        }

        List<String> userRoleKeys  = objectMapper.readValue(userRolesValue, new TypeReference<>() {});

        if (CollUtil.isNotEmpty(userRoleKeys)) {
            List<String> rolePermissionKeys = userRoleKeys.stream().
                    map(RedisKeyConstants::buildRolePermissionsKey).
                    toList();

            List<String> rolePermissionsValues  = redisTemplate.opsForValue().multiGet(rolePermissionKeys);
            if(CollUtil.isNotEmpty(rolePermissionsValues)){
                List<String> permissionList = Lists.newArrayList();
                rolePermissionsValues.forEach( jsonValue -> {
                    try {
                        List<String> rolePermissions = objectMapper.readValue(jsonValue, new TypeReference<>() {});
                        permissionList.addAll(rolePermissions);

                    }catch (Exception e){
                        log.warn("Json解析错误");
                    }

                });
                return permissionList;
            }
        }
        return null;
    }


    /**
     *
     * @param loginId  账号id
     * @param loginType 账号类型
     * @return
     */
    @Override
    @SneakyThrows
    public List<String> getRoleList(Object loginId, String loginType) {
        log.info("## 获取用户角色列表, loginId: {}", loginId);
        String userRoleKey = RedisKeyConstants.buildUserRoleKey(Long.valueOf(loginId.toString()));
        String userRolesValue  = redisTemplate.opsForValue().get(userRoleKey);

        if (StringUtils.isBlank(userRolesValue)) {
            return null;
        }

        return objectMapper.readValue(userRolesValue, new TypeReference<>() {});

    }
}
