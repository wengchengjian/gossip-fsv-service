package com.weng.fsv.plugin.minio.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.weng.fsv.model.base.Result;
import com.weng.fsv.plugin.minio.config.FsvPluginMinioProperties;
import com.weng.fsv.plugin.minio.service.MinioService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * minio文件相关接口
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/fsv/file")
@RequiredArgsConstructor
public class MinioFileController {
    private final MinioService minioService;

    private final FsvPluginMinioProperties prop;

    @GetMapping("/bucketExists")
    public Result<Boolean> bucketExists(@RequestParam("bucketName") String bucketName) throws Exception {
        return Result.success(minioService.bucketExists(bucketName));
    }

    @GetMapping("/makeBucket")
    public Result<Boolean> makeBucket(String bucketName) throws Exception {
        minioService.makeBucket(bucketName);
        return Result.success();
    }

    @GetMapping("/removeBucket")
    public Result<Boolean> removeBucket(String bucketName) throws Exception {
        minioService.removeBucket(bucketName);
        return Result.success();
    }

    @GetMapping("/getAllBuckets")
    public Result<List<Bucket>> getAllBuckets() throws Exception {
        return Result.success(minioService.getAllBuckets());
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String objectName = minioService.upload(prop.getBucketName(), file);
        if (null != objectName) {
            return Result.success(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName);
        }
        return Result.failure();
    }

    @GetMapping("/preview")
    public Result<String> preview(@RequestParam("fileName") String fileName) throws Exception {
        return Result.success(minioService.preview(fileName));
    }

    @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse res) throws Exception {
        minioService.download(prop.getBucketName(), fileName, res);
    }

    @PostMapping("/delete")
    public Result<String> remove(String url) throws Exception {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName() + "/") + prop.getBucketName().length() + 1);
        minioService.remove(prop.getBucketName(), objName);
        return Result.success(objName);
    }

}
