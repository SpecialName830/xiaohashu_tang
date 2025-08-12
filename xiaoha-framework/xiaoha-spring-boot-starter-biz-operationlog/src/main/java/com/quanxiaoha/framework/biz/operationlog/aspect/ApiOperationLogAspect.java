package com.quanxiaoha.framework.biz.operationlog.aspect;

import com.quanxiaoha.framework.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Slf4j
public class ApiOperationLogAspect {

    @Pointcut("@annotation(com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog)")
    public void apiOperationLog() {
    }

    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        List<String> argsStr = Arrays.stream(joinPoint.getArgs()).map(JsonUtils::toJsonString).toList();
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String description = methodSignature.getMethod().getAnnotation(ApiOperationLog.class).description();
        long startTime = System.currentTimeMillis();
        log.info("[{}] 开始执行, 入参:{},请求类:{}, 方法:{}", description, argsStr, className, methodName);
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - startTime;
        log.info("[{}] 执行耗时:{}ms, 出参:{}", description, time, JsonUtils.toJsonString(result));
        return result;
    }

    Function<Object, String> toJsonStr(){
        return JsonUtils::toJsonString;
    }
}
