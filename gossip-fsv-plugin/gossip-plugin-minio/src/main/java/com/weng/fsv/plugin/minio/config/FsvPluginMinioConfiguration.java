package com.weng.fsv.plugin.minio.config;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "com.weng.fsv.plugin.minio")
public class FsvPluginMinioConfiguration {
    @PostConstruct
    public void init() {
        log.info("fsv plugin minio module is loaded successful! ");
    }
}
