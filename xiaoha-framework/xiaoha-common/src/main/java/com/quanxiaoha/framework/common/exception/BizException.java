package com.quanxiaoha.framework.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class BizException extends RuntimeException {

    String errorMessage;

    String errorCode;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorMessage = baseExceptionInterface.getErrorMessage();
        this.errorCode = baseExceptionInterface.getErrorCode();
    }
}
