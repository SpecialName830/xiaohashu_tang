package com.quanxiaoha.xiaohashu.auth.model.vo.veriticationcode;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendVerificationCodeReqVO {

    @NotBlank(message = "电话号不能为空")
    private String phone;

}
