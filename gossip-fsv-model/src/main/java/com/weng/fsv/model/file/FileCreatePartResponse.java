package com.weng.fsv.model.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建文件分片响应对象
 */
@Data
@Accessors(chain = true)
public class FileCreatePartResponse {

    @JsonIgnore
    private String uploadId;

    private String fileName;

    private Integer partSize;

    @JsonIgnore
    private String objectKey;
}
