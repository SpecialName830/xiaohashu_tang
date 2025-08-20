package com.quanxiaoha.xiaohashu.user.biz.enums;

import com.quanxiaoha.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ResponseCodeEnum implements BaseExceptionInterface {

    SYSTEM_ERROR("USER-10001", "出错啦 后台小哥正在修复中..."),
    PARAM_NOT_VALID("USER-10001", "参数错误"),
    NICK_NAME_VALID_FAIL("USER-20001", "昵称请设置2-24个字符，不能使用@《/等特殊字符"),
    XIAOHASHU_ID_VALID_FAIL("USER-20002", "请设置小哈书号6-15个字符,仅可使用英文(必须),数字，下划线"),
    SEX_VALID_FAIL("USER-20003", "性别错误"),
    INTRODUCING_VALID_FAIL("USER-20004", "个人简介请设置1-100个字符"),

    ;

    private final String errorCode;
    private final String errorMessage;
}
