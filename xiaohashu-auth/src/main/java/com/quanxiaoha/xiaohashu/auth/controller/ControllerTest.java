package com.quanxiaoha.xiaohashu.auth.controller;

import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ControllerTest {

    @GetMapping("/test")
    @ApiOperationLog(description = "测试用例")
    public Response<String> test(){
        return Response.success();
    }

    @GetMapping("/testUser")
    @ApiOperationLog(description = "测试用户")
    public Response<User> testUser(){
        return Response.success(User.builder().nickName("卢本伟")
                .time(LocalDateTime.now())
                .build());
    }


}
