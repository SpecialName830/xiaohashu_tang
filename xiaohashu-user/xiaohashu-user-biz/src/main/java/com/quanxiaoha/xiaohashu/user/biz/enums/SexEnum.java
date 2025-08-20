package com.quanxiaoha.xiaohashu.user.biz.enums;


import com.quanxiaoha.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum SexEnum{


    MAN(1),
    WOMAN(0),
    ;

    private final Integer value;

    public static boolean isValid(Integer value){
        for (SexEnum sexEnum : SexEnum.values()) {
            if (Objects.equals(sexEnum.value,value)) {
                return true;
            }
        }
        return false;
    }
}
