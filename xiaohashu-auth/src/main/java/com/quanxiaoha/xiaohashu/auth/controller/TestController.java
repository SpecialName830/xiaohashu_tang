package com.quanxiaoha.xiaohashu.auth.controller;

import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.alarm.AlarmInterface;
import com.quanxiaoha.xiaohashu.auth.domain.dataobject.UserDO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {

    @Resource
    AlarmInterface alarmInterface;

    @GetMapping("/test")
    @ApiOperationLog(description = "测试用例")
    public Response<String> test() {
        return Response.success();
    }

    @GetMapping("/testUser")
    @ApiOperationLog(description = "测试用户")
    public Response<UserDO> testUser() {
        return Response.success();
    }

    @GetMapping("/alarm")
    public Response<?> testAlarm() {
        return Response.success(alarmInterface.sendMessage());
    }


}
