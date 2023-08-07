package com.weng.fsv.model.file.dto;

import lombok.Data;

/**
 * 文件合并/完成实体
 */
@Data
public class FileCompleteDTO {
    private String bucketName;

    private String uploadId;

    private String objectName;
}
