package com.quanxiaoha.xiaohashu.auth.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.model.vo.user.UserLoginReqVO;
import jakarta.annotation.Resource;

public interface UserService {
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);

    Response<?> logout(Long userId);
}
