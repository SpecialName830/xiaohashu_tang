package com.quanxiaoha.framework.common.response;

import com.quanxiaoha.framework.common.exception.BaseExceptionInterface;
import com.quanxiaoha.framework.common.exception.BizException;
import lombok.Data;
import java.io.Serializable;


@Data
public class Response<T> implements Serializable {

    private boolean isSuccess = true;

    private String message;

    private String code;

    private T data;

    /* <=====成功相应=====> */
    public static <T> Response<T> success(){
        return new Response<>();
    }


    public static <T> Response<T> success(T data){
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    /* <=====失败相应=====> */

    public static <T> Response<T> fail(){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(T data){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(String errorMsg){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMsg);
        return response;
    }
    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(baseExceptionInterface.getErrorMessage());
        response.setCode(baseExceptionInterface.getErrorCode());
        return response;
    }
    public static <T> Response<T> fail(String errorCode, String errorMsg){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(errorCode);
        response.setMessage(errorMsg);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(bizException.getErrorMessage());
        response.setCode(bizException.getErrorCode());
        return response;
    }



}
