package com.quanxiaoha.xiaohashu.user.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUserInfoReqVO {

    /**
     * 头像
     */
    private MultipartFile avater;

    private String nickname;

    private String xiaohashuId;

    private Integer sex;

    private LocalDate birthday;

    private String introduction;

    private MultipartFile backgroundimg;
}
