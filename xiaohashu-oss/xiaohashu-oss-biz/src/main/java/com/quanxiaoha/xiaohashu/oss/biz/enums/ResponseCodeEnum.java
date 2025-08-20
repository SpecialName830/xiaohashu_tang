package com.quanxiaoha.xiaohashu.oss.biz.enums;

import com.quanxiaoha.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor

public enum ResponseCodeEnum implements BaseExceptionInterface {
    SYSTEM_ERROR("OSS-10000","出错啦...小哥正在努力修复中"),
    PARAM_NOT_VALID("OSS-10001","参数错误")
    ;

    private final String errorCode;
    private final String errorMessage;
}
