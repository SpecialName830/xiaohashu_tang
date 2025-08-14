package com.quanxiaoha.framework.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {

    ENABLE(0),
    DISABLE(1),
    ;

    private final Integer value;
}