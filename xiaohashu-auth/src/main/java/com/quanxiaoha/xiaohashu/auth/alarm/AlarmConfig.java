package com.quanxiaoha.xiaohashu.auth.alarm;

import com.quanxiaoha.xiaohashu.auth.alarm.Impl.MailAlarmHelper;
import com.quanxiaoha.xiaohashu.auth.alarm.Impl.SmsAlarmHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class AlarmConfig {

    @Value("${alarm.type}")
    private String type;


    @RefreshScope
    @Bean
    public AlarmInterface alarmInterface() {
        if(type.equals("sms")){
            return new SmsAlarmHelper();
        }else if(type.equals("email")){
            return new MailAlarmHelper();
        }
        return null;
    }
}
