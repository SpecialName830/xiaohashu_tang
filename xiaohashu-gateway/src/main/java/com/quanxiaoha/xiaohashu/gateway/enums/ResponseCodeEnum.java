package com.quanxiaoha.xiaohashu.gateway.enums;

import com.quanxiaoha.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum implements BaseExceptionInterface {

    SYSTEM_ERROR("500", "系统繁忙请稍后再试..."),
    UNAUTHORIZED("401","暂无权限")
    ;

    private final String errorCode;
    private final String errorMessage;
    }
