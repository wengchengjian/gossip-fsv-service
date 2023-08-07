package com.weng.fsv.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件分片合并结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMergePartResponse {
    private String uploadId;

    private int completedParts;

    private int size;
}
