package com.quanxiaoha.xiaohashu.auth.domain.mapper;

import com.quanxiaoha.xiaohashu.auth.domain.dataobject.UserDO;

public interface UserDOMapper {
    UserDO selectByPhone(String phone);

    void insert(UserDO userDO);
}