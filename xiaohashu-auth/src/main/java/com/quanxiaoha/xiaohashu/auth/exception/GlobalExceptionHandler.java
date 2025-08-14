package com.quanxiaoha.xiaohashu.auth.exception;

import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.enums.ResponseCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler({ BizException.class })
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException exception){
        log.warn("{} request fail, errCode: {}, errorMsg: {}", request.getRequestURL(), exception.getErrorCode(), exception.getErrorMessage());
        return Response.fail(exception);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder sb = new StringBuilder();

        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors ->{
            errors.forEach(error->
                    sb.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append("当前值: '")
                            .append(error.getRejectedValue())
                            .append("';")
            );
        });

        String errorMsg = sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURL(), errorCode, errorMsg);
        return Response.fail(errorCode, errorMsg);
    }


    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Response<Object> handleException(HttpServletRequest request, Exception e){
        log.warn("{} request error,errorMessage: {}",request.getRequestURL(), e.getMessage());
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public Response<Object> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
        String errorCode = ResponseCodeEnum.SYSTEM_ERROR.getErrorCode();
        log.warn("{} request fail, errorMessage:{}", request.getRequestURL(), exception.getMessage());
        return Response.fail(errorCode, exception.getMessage());
    }

}
