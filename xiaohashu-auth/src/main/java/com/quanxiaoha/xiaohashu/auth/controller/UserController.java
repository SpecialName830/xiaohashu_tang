package com.quanxiaoha.xiaohashu.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.domain.mapper.UserDOMapper;
import com.quanxiaoha.xiaohashu.auth.model.vo.user.UpdatePasswordReqVO;
import com.quanxiaoha.xiaohashu.auth.model.vo.user.UserLoginReqVO;
import com.quanxiaoha.xiaohashu.auth.service.UserService;
import com.quanxiaoha.xiaohashu.framework.biz.context.Holder.LoginUserContextHolder;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录注册")
    public Response<String> login(@RequestBody @Validated UserLoginReqVO userLoginReqVO) {
        return userService.loginAndRegister(userLoginReqVO);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "用户登出操作")
    public Response<?> logout() {
        return userService.logout();
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "更改密码操作")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO updatePasswordReqVO) {
        return userService.updatePassword(updatePasswordReqVO);
    }

    @PostMapping("/isLogin")
    @ApiOperationLog(description = "验证是否登录")
    public boolean isLogin(){
        Long userId = LoginUserContextHolder.getUserId();
        return StpUtil.isLogin(userId);
    }

}
