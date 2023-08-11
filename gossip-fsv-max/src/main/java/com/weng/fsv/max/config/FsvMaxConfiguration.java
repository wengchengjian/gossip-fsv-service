package com.weng.fsv.max.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wengchengjian
 * @date 2023/8/9-10:35
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "com.weng.fsv.max")
public class FsvMaxConfiguration {
    @PostConstruct
    public void init() {
        log.info("fsv max module is loaded successful! ");
    }
}
