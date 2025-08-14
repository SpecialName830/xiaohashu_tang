package com.quanxiaoha.xiaohashu.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum LoginTypeEnum {

    VERIFICATION_CODE(1),
    PASSWORD_CODE(2);

    private final Integer value;

    public static LoginTypeEnum valueOf(int type){
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if(loginTypeEnum.getValue().equals(type)){
                return loginTypeEnum;
            }
        }
        return null;
    }
}
