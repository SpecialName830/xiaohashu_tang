package com.quanxiaoha.xiaohashu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaohashu.auth.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaohashu.auth.model.vo.veriticationcode.SendVerificationCodeReqVO;
import com.quanxiaoha.xiaohashu.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.quanxiaoha.xiaohashu.auth.sms.AliyunSmsHelper;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private  AliyunSmsHelper aliyunSmsHelper;

    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        String phone = sendVerificationCodeReqVO.getPhone();
        String verificationCodeKey = RedisKeyConstants.buildVerificationCodeKey(phone);

        Boolean isSend = redisTemplate.hasKey(verificationCodeKey);
        if(Boolean.TRUE.equals(isSend)){
            throw  new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        // 随机生成六位验证码
        String verificationCode = RandomUtil.randomNumbers(6);

        log.info("===> 手机号:{} 已经发送短信验证码:{}", phone, verificationCode);
        /*redisTemplate.opsForValue().set(verificationCodeKey, verificationCode);*/

        threadPoolTaskExecutor.submit(() -> {
            String signName = "阿里云短信测试";
            String templateCode = "SMS_154950909";
            String templateParam = String.format("{\"code\":\"%s\"}", verificationCode);
            aliyunSmsHelper.sendMessage(signName,templateCode,phone,templateParam);
        });
        redisTemplate.opsForValue().set(verificationCodeKey, verificationCode, 3, TimeUnit.MINUTES);

        return Response.success();
    }
}