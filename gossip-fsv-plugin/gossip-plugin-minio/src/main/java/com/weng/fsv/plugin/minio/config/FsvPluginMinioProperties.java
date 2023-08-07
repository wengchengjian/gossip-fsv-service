package com.weng.fsv.plugin.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * minio 属性类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class FsvPluginMinioProperties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
