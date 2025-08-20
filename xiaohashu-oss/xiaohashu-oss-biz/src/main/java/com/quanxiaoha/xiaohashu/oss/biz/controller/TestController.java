package com.quanxiaoha.xiaohashu.oss.biz.controller;


import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping( "/file")
public class TestController {

    @PostMapping(value = "/test")
    @ApiOperationLog(description = "OpenFeign 测试接口")
    public Response<?> test() {
        return Response.success();
    }
}
