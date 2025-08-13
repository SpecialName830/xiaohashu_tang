package com.quanxiaoha.xiaohashu.auth.sms;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.quanxiaoha.framework.common.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AliyunSmsHelper {

    @Resource
    private Client client;

    public boolean sendMessage(String signName, String templateCode, String phone, String templateParam){
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam(templateParam);

        RuntimeOptions runtime = new RuntimeOptions();

        try {
            SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info("短信发送成功{}", JsonUtils.toJsonString(response));
            return true;
        }catch (Exception error){
            log.error("短信发送错误:",error);
            return false;
        }
    }
}
