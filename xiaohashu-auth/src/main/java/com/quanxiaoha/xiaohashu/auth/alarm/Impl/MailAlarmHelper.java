package com.quanxiaoha.xiaohashu.auth.alarm.Impl;

import com.quanxiaoha.xiaohashu.auth.alarm.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailAlarmHelper implements AlarmInterface {
    @Override
    public String sendMessage() {
        log.info("email发送信息");
        return "email";
    }
}
