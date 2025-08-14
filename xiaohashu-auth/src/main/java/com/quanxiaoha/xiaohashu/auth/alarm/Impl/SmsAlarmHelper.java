package com.quanxiaoha.xiaohashu.auth.alarm.Impl;

import com.quanxiaoha.xiaohashu.auth.alarm.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsAlarmHelper implements AlarmInterface {
    @Override
    public String sendMessage() {
        log.info("sms发送信息");
        return "sms";
    }
}
