package com.quanxiaoha.xiaohashu.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaohashu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.PermissionDO;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.RoleDO;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.RolePermissionDO;
import com.quanxiaoha.xiaohashu.auth.domain.mapper.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    RoleDOMapper roleDOMapper;

    @Resource
    RolePermissionDOMapper rolePermissionDOMapper;

    @Resource
    PermissionDOMapper permissionDOMapper;

    // 权限同步标记 Key
    private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");

        try {
            Boolean canPush = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);

            if(!canPush){
                log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }

            List<RoleDO> roleDOS = roleDOMapper.selectEnableList();
            if (CollUtil.isNotEmpty(roleDOS)) {
                List<Long> roleIDS = roleDOS.stream().map(RoleDO::getId).toList();
                List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIDS);

                List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();

                Map<Long, List<Long>> rolePermissionListMap = rolePermissionDOS.stream().collect(
                        Collectors.groupingBy(RolePermissionDO::getRoleId,
                                Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList()))
                );

                Map<Long, PermissionDO> permissionDOMap = permissionDOS.stream().collect(
                        Collectors.toMap(PermissionDO::getId, PermissionDO -> PermissionDO)
                );

                Map<String, List<String>> roleIdPermissionDOMap = Maps.newHashMap();

                roleDOS.forEach(roleDO -> {
                    Long roleDOId = roleDO.getId();
                    String roleKey = roleDO.getRoleKey();
                    List<Long> permissionIdList = rolePermissionListMap.get(roleDOId);
                    if (CollUtil.isNotEmpty(permissionIdList)) {
                        List<PermissionDO> permissionDOList = Lists.newArrayList();
                        permissionIdList.forEach(permissionId -> {
                            PermissionDO permissionDO = permissionDOMap.get(permissionId);
                            if (Objects.nonNull(permissionDO)) {
                                permissionDOList.add(permissionDO);
                            }
                        });
                        List<String> permissionKeyList = permissionDOList.stream().map(PermissionDO::getPermissionKey).toList();
                        roleIdPermissionDOMap.put(roleKey, permissionKeyList);
                    }
                });

                roleIdPermissionDOMap.forEach((roleKey, permissionKey) -> {
                    String rolePermissionsKey = RedisKeyConstants.buildRolePermissionsKey(roleKey);
                    redisTemplate.opsForValue().set(rolePermissionsKey, JsonUtils.toJsonString(permissionKey));
                });
            }
        }catch (Exception e){
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }

        log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
    }
}
