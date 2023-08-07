package com.weng.fsv.plugin.minio.service;

import com.amazonaws.services.s3.model.*;

import com.weng.fsv.model.file.FileCreatePartResponse;
import com.weng.fsv.model.file.dto.FileMultipartDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 包含对minio操作的service
 */
public interface MinioService {
    boolean bucketExists(String bucketName) throws Exception;

    boolean objectExist(String bucket, String objectKey);

    /**
     * 创建存储bucket
     * @return Boolean
     */
    void makeBucket(String bucketName) throws Exception;
    /**
     * 删除存储bucket
     * @return Boolean
     */
    void removeBucket(String bucketName) throws Exception;
    /**
     * 获取全部bucket
     */
    List<Bucket> getAllBuckets() throws Exception;

    /**
     * 创建分片文件上传url
     * @param fileMultipartDTO
     * @return
     * @throws Exception
     */
    FileCreatePartResponse createMultipartUpload(FileMultipartDTO fileMultipartDTO) throws Exception;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    String upload(String bucket, MultipartFile file) throws Exception;

    /**
     * 预览图片
     * @param fileName 文件名
     * @return 预览文件地址
     */
    String preview(String fileName) throws Exception;

    /**
     * 文件下载
     * @param fileName 文件名称
     * @param res response
     */
    void download(String bucket, String fileName, HttpServletResponse res) throws Exception;

    /**
     * 查看文件对象
     * @return 存储bucket内文件对象信息
     */
    List<S3ObjectSummary> listObjects(String bucket, String prefix) throws Exception;

    /**
     * 删除
     * @param fileName 文件名
     */
    void remove(String bucket, String fileName) throws Exception;

    String generatePresignedUrl(String bucketName, String objectKey, Map<String, String> params);

    void merge(String bucketName, String objectKey, String uploadId, Integer totalIndex);

    PartListing listParts(ListPartsRequest listPartsRequest);

    PutObjectResult putObject(String bucket, String key, InputStream input, ObjectMetadata metadata);
}
