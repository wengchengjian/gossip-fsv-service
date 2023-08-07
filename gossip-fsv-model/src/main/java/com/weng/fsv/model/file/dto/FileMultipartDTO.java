package com.weng.fsv.model.file.dto;

import lombok.Data;

/**
 * 文件上传dto
 */
@Data
public class FileMultipartDTO {

    private String fileName;

    private String folder;

    private Integer fileSize;

    private String md5;

    private Integer chunkSize;

    private String bucketName;
}
