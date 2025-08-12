package com.quanxiaoha.framework.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class BizException implements BizExceptionInterface {

    String errorMessage;

    String errorCode;

    public BizException(BizExceptionInterface bizExceptionInterface) {
        this.errorMessage = bizExceptionInterface.getErrorMessage();
        this.errorCode = bizExceptionInterface.getErrorCode();
    }
}
