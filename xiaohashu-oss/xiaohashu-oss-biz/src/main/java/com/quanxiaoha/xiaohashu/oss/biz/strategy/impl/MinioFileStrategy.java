package com.quanxiaoha.xiaohashu.oss.biz.strategy.impl;

import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.oss.biz.config.MinioConfig;
import com.quanxiaoha.xiaohashu.oss.biz.config.MinioProperties;
import com.quanxiaoha.xiaohashu.oss.biz.strategy.FileStrategy;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
public class MinioFileStrategy implements FileStrategy  {

    @Resource
    MinioProperties minioProperties;

    @Resource
    MinioClient minioClient;


    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String bucketName) {

        log.info("## 上传文件至 Minio ...");
        if (file == null || file.getSize() == 0) {
            log.error("文件大小异常");
            throw new RuntimeException("文件大小不能为空");
        }

        String originalFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String key = UUID.randomUUID().toString().replace("-", "");

        // 获取文件的后缀，如 .jpg
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        String objectName  = String.format("%s%s", key, suffix);

        log.info("===> 开始上传至minio, objectName: {}", objectName);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(contentType)
                .build());

        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
        log.info("上传文件的访问路径:{}", url);
        return url;
    }

}
