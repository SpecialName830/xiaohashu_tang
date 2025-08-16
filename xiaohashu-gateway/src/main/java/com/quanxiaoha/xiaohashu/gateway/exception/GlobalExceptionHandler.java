package com.quanxiaoha.xiaohashu.gateway.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.gateway.enums.ResponseCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
// 网关服务中执行 ErrorWebExceptionHandler 来将全局异常处理器增添到spring容器中
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Resource
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        log.error("==> 捕获异常信息", ex);

        Response<?> result;
        if(ex instanceof NotLoginException){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            result = Response.fail(ResponseCodeEnum.UNAUTHORIZED.getErrorCode(), "未携带token令牌");
        }else if (ex instanceof NotPermissionException){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            result = Response.fail(ResponseCodeEnum.UNAUTHORIZED.getErrorCode(), ResponseCodeEnum.UNAUTHORIZED.getErrorMessage());
        }else{
            result = Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response.writeWith(Mono.fromSupplier( () -> {
            DataBufferFactory bufferFactory = response.bufferFactory();

            try {
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
            }catch (Exception e){
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
