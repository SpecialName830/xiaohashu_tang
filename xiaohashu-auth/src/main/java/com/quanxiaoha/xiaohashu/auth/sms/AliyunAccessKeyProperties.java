package com.quanxiaoha.xiaohashu.auth.sms;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunAccessKeyProperties {

    @Value("${AliyunAccessKeyId}")
    private String accessKeyId;

    @Value("${AliyunAccessKeySecret}")
    private String accessKeySecret;

}
