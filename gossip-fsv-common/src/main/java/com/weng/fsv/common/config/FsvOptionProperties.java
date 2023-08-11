package com.weng.fsv.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wengchengjian
 * @date 2023/8/11-9:21
 */
@Data
@ConfigurationProperties(prefix = "fsv.options")
public class FsvOptionProperties {

    /**
     * 记录审计日志
     */
    private boolean logging = true;
}
