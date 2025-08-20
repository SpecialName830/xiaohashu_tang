package com.quanxiaoha.xiaohashu.oss.biz.service.Impl;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.oss.biz.service.FileService;
import com.quanxiaoha.xiaohashu.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private FileStrategy fileStrategy;


    @Override
    public Response<?> uploadFile(MultipartFile file) {

        String url = fileStrategy.uploadFile(file, "xiaohashu");
        return Response.success(url);
    }
}
