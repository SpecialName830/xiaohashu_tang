package com.quanxiaoha.xiaohashu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.quanxiaoha.framework.common.enums.DeletedEnum;
import com.quanxiaoha.framework.common.enums.StatusEnum;
import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaohashu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaohashu.auth.constant.RoleConstants;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.RoleDO;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.UserDO;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.UserRoleDO;
import com.quanxiaoha.xiaohashu.auth.domain.mapper.RoleDOMapper;
import com.quanxiaoha.xiaohashu.auth.domain.mapper.UserDOMapper;
import com.quanxiaoha.xiaohashu.auth.domain.mapper.UserRoleDOMapper;
import com.quanxiaoha.xiaohashu.auth.enums.LoginTypeEnum;
import com.quanxiaoha.xiaohashu.auth.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaohashu.auth.model.vo.user.UserLoginReqVO;
import com.quanxiaoha.xiaohashu.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.quanxiaoha.xiaohashu.auth.enums.LoginTypeEnum.PASSWORD_CODE;
import static com.quanxiaoha.xiaohashu.auth.enums.LoginTypeEnum.VERIFICATION_CODE;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDOMapper userDOMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserRoleDOMapper userRoleDOMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    RoleDOMapper roleDOMapper;

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);

        Long userId = null;
        switch (loginTypeEnum){
            case VERIFICATION_CODE :
                String code = userLoginReqVO.getCode();
                Preconditions.checkArgument(StringUtils.isNotBlank(code),"验证码不能为空");

                String verificationCodeKey = RedisKeyConstants.buildVerificationCodeKey(phone);

                String sentCode = (String) redisTemplate.opsForValue().get(verificationCodeKey);
                if(!StringUtils.equals(sentCode, code)){
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                UserDO userDO = userDOMapper.selectByPhone(phone);
                log.info("用户是否注册: phone:{} user:{}",phone, JsonUtils.toJsonString(userDO));

                if(Objects.isNull(userDO)){
                    // todo 注册
                    userId = registerUser(phone);
                }else{
                    userId = userDO.getId();
                }

                break;

            case PASSWORD_CODE :
                // todo
                break;

            default:
                break;
        }
        StpUtil.login(userId);

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return Response.success(tokenInfo.tokenValue);
    }

    @Override
    public Response<?> logout(Long userId) {
        StpUtil.logout(userId);
        return Response.success();
    }

    /**
     * 系统自动注册用户
     * @param phone
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    // 当他是被调用的情况下 可能会失效

    public Long registerUser(String phone) {

        return transactionTemplate.execute(status -> {
            try {
                Long xiaohashuId = redisTemplate.opsForValue().increment(RedisKeyConstants.XIAOHASHU_ID_GENERATOR_KEY);
                UserDO userDO = UserDO.builder()
                        .phone(phone)
                        .xiaohashuId(String.valueOf(xiaohashuId))
                        .nickname("小红薯"+xiaohashuId)
                        .status(StatusEnum.ENABLE.getValue())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.getValue())
                        .build();

                userDOMapper.insert(userDO);

                Long userDOId = userDO.getId();

                // 给用户分配一个默认角色
                UserRoleDO userRoleDO = UserRoleDO.builder()
                        .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                        .userId(userDOId)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.getValue())
                        .build();
                userRoleDOMapper.insert(userRoleDO);
                List<String> roles = new ArrayList<>(1);
                RoleDO role = roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);
                roles.add(role.getRoleKey());
                String userRoleKey = RedisKeyConstants.buildUserRoleKey(userDOId);
                redisTemplate.opsForValue().set(userRoleKey, JsonUtils.toJsonString(roles));

                return userDOId;
            }catch (Exception e){
                status.setRollbackOnly();
                log.error("用户注册异常:", e);
                return null;
            }
        });

    }
}
