package com.weng.fsv.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author wengchengjian
 * @date 2023/8/11-14:27
 */
@Data
@ConfigurationProperties(prefix = "fsv.code")
public class FsvCodeProperties {
    /**
     * 验证码超时时间
     */
    private Duration timeout = Duration.ofMinutes(3);

}
