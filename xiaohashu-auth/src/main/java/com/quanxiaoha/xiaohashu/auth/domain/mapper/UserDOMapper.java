package com.quanxiaoha.xiaohashu.auth.domain.mapper;

import com.quanxiaoha.xiaohashu.auth.domain.dataobject.UserDO;
import com.quanxiaoha.xiaohashu.auth.model.vo.user.UpdatePasswordReqVO;

public interface UserDOMapper {
    UserDO selectByPhone(String phone);

    void insert(UserDO userDO);

    void updateByPrimaryKeySelective(UserDO userDO);
}