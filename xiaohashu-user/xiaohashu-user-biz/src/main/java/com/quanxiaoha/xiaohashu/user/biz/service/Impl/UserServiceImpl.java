package com.quanxiaoha.xiaohashu.user.biz.service.Impl;

import com.google.common.base.Preconditions;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.framework.biz.context.Holder.LoginUserContextHolder;
import com.quanxiaoha.xiaohashu.oss.api.TestFeignController;
import com.quanxiaoha.xiaohashu.user.biz.domain.dataobject.UserDO;
import com.quanxiaoha.xiaohashu.user.biz.domain.mapper.UserDOMapper;
import com.quanxiaoha.xiaohashu.user.biz.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaohashu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.quanxiaoha.xiaohashu.user.biz.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.quanxiaoha.framework.common.util.ParamUtils.*;
import static com.quanxiaoha.xiaohashu.user.biz.enums.SexEnum.isValid;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDOMapper userDOMapper;

    @Resource
    TestFeignController testFeignController;

    @Override
    public Response<?> updateUserinfo(UpdateUserInfoReqVO updateUserInfoReqVO) {
        UserDO userDO = new UserDO();
        userDO.setId(LoginUserContextHolder.getUserId());
        boolean needUpdate = false;

        MultipartFile avater = updateUserInfoReqVO.getAvater();

        if (Objects.nonNull(avater)) {
            // TODO 调用对象存储服务上传文件
            testFeignController.test();
        }

        String nickName = updateUserInfoReqVO.getNickname();
        if (StringUtils.isNotBlank(nickName)) {
            Preconditions.checkArgument(checkNickName(nickName), ResponseCodeEnum.NICK_NAME_VALID_FAIL.getErrorMessage());
            userDO.setNickname(nickName);
            needUpdate = true;
        }

        String xiaohashuId = updateUserInfoReqVO.getXiaohashuId();
        if (StringUtils.isNotBlank(xiaohashuId)) {
            Preconditions.checkArgument(checkXiaohashuId(xiaohashuId), ResponseCodeEnum.XIAOHASHU_ID_VALID_FAIL.getErrorMessage());
            userDO.setXiaohashuId(xiaohashuId);
            needUpdate = true;
        }

        Integer sex = updateUserInfoReqVO.getSex();
        if (Objects.nonNull(sex)) {
            Preconditions.checkArgument(isValid(sex), ResponseCodeEnum.SEX_VALID_FAIL.getErrorMessage());
            userDO.setSex(sex);
            needUpdate = true;
        }

        LocalDate birthday = updateUserInfoReqVO.getBirthday();
        if (Objects.nonNull(birthday)) {
            userDO.setBirthday(birthday);
            needUpdate = true;
        }

        String introduction = updateUserInfoReqVO.getIntroduction();
        if (StringUtils.isNotBlank(introduction)) {
            Preconditions.checkArgument(checkStrLength(introduction, 100), ResponseCodeEnum.INTRODUCING_VALID_FAIL.getErrorMessage());
            userDO.setIntroduction(introduction);
            needUpdate = true;
        }

        MultipartFile backgroundimg = updateUserInfoReqVO.getBackgroundimg();
        if (Objects.nonNull(backgroundimg)) {
            // todo 调用对象服务储存上传系统
        }

        if (needUpdate) {
            userDO.setCreateTime(LocalDateTime.now());
            userDOMapper.updateByPrimaryKeySelective(userDO);
        }


        return Response.success();
    }
}
