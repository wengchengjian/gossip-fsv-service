package com.weng.fsv.plugin.minio.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.weng.fsv.model.file.FileCreatePartResponse;
import com.weng.fsv.model.file.dto.FileMultipartDTO;
import com.weng.fsv.plugin.minio.config.FsvPluginMinioProperties;
import com.weng.fsv.plugin.minio.config.MinioConstant;
import com.weng.fsv.plugin.minio.service.MinioService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MinioService 实现类
 */
@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    private static final Integer DEFAULT_TIME_OUT = 2000;

    @Resource
    private AmazonS3 minioClient;

    @Resource
    private FsvPluginMinioProperties prop;
    /**
     * 查看存储bucket是否存在
     * @return boolean
     */
    @Override
    public boolean bucketExists(String bucketName) {
        return minioClient.doesBucketExistV2(bucketName);
    }

    @Override
    public boolean objectExist(String bucket, String objectKey) {
        return minioClient.doesObjectExist(bucket, objectKey);
    }

    /**
     * 创建存储bucket
     */
    @Override
    public void makeBucket(String bucketName) {
        minioClient.createBucket(bucketName);
    }
    /**
     * 删除存储bucket
     */
    @Override
    public void removeBucket(String bucketName) {
        minioClient.deleteBucket(bucketName);
    }
    /**
     * 获取全部bucket
     */
    @Override
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    @Override
    public FileCreatePartResponse createMultipartUpload(FileMultipartDTO fileMultipartDTO) {
        log.info("fileMultipartDTO:{}", fileMultipartDTO);
        String fileName = fileMultipartDTO.getFileName();
        Assert.hasText(fileName, "文件名不能为空");
        Integer chunkSize = fileMultipartDTO.getChunkSize();
        String bucketName = Optional.ofNullable(fileMultipartDTO.getBucketName()).orElse(prop.getBucketName());
        if (!bucketExists(bucketName)) {
            makeBucket(bucketName);
        }
        // 1. 根据文件名创建签名
        // 2. 获取uploadId
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Date currentDate = new Date();
        String key = StrUtil.format("{}/{}.{}", DateUtil.format(currentDate, "YYYY-MM-dd"), IdUtil.randomUUID(), suffix);
        String contentType = MediaTypeFactory.getMediaType(key).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        InitiateMultipartUploadResult initiateMultipartUploadResult = minioClient
                .initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName, key).withObjectMetadata(objectMetadata));
        String uploadId = initiateMultipartUploadResult.getUploadId();
        return new FileCreatePartResponse().setUploadId(uploadId).setFileName(fileName).setObjectKey(key);
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    @Override
    public String upload(String bucket, MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new RuntimeException("文件名不能为空");
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Date currentDate = new Date();
        String key = StrUtil.format("{}/{}.{}", DateUtil.format(currentDate, "YYYY-MM-dd"), IdUtil.randomUUID(), suffix);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        //文件名称相同会覆盖
        putObject(bucket, key, file.getInputStream(), objectMetadata);
        return key;
    }

    /**
     * 预览图片
     */
    @Override
    public String preview(String fileName) {
        // 查看文件地址
        return "";
    }

    /**
     * 文件下载
     * @param fileName 文件名称
     * @param res response
     */
    @Override
    public void download(String bucket, String fileName, HttpServletResponse res) {
        try (S3ObjectInputStream fileStream = minioClient.getObject(bucket, fileName).getObjectContent()) {
            byte[] buf = new byte[4096];
            int len;
            res.setCharacterEncoding("utf-8");
            res.setContentLengthLong(fileStream.available());
            // 设置强制下载不打开
            // res.setContentType("application/force-download");
            res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            try (ServletOutputStream out = res.getOutputStream()) {
                fileStream.transferTo(out);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }

    /**
     * 查看文件对象
     *
     * @return 存储bucket内文件对象信息
     */
    public List<S3ObjectSummary> listObjects(String bucket, String prefix) {
        return minioClient.listObjects(bucket, prefix).getObjectSummaries();
    }

    /**
     *
     * @param fileName 删除文件名
     */
    @Override
    public void remove(String bucket, String fileName) {
        minioClient.deleteObject(bucket, fileName);
    }

    @Override
    public String generatePresignedUrl(String bucketName, String objectKey, Map<String, String> params) {
        Date currentDate = new Date();
        Date expireDate = DateUtil.offsetMillisecond(currentDate, MinioConstant.PRE_SIGN_URL_EXPIRE.intValue());
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withExpiration(expireDate).withMethod(HttpMethod.PUT);
        if (params != null) {
            params.forEach(request::addRequestParameter);
        }
        URL preSignedUrl = minioClient.generatePresignedUrl(request);
        return preSignedUrl.toString();
    }

    @Override
    public void merge(String bucketName, String objectKey, String uploadId, Integer totalIndex) {
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, uploadId);
        PartListing partListing = minioClient.listParts(listPartsRequest);
        List<PartSummary> parts = partListing.getParts();
        if (!totalIndex.equals(parts.size())) {
            // 已上传分块数量与记录中的数量不对应，不能合并分块
            throw new RuntimeException("分片缺失，请重新上传");
        }
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest()
                .withUploadId(uploadId)
                .withKey(objectKey)
                .withBucketName(bucketName)
                .withPartETags(parts.stream().map(partSummary -> new PartETag(partSummary.getPartNumber(), partSummary.getETag())).collect(Collectors.toList()));
        minioClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    @Override
    public PartListing listParts(ListPartsRequest listPartsRequest) {
        return minioClient.listParts(listPartsRequest);
    }

    @Override
    public PutObjectResult putObject(String bucket, String key, InputStream input, ObjectMetadata metadata) {
        if (!bucketExists(bucket)) {
            makeBucket(bucket);
        }
        return minioClient.putObject(bucket, key, input, metadata);
    }
}
