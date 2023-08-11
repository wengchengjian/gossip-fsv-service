package com.weng.fsv.common.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wengchengjian
 * @date 2023/8/4-9:19
 */
@Slf4j
@EnableScheduling
@EnableAsync
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EntityScan("com.weng.fsv.model")
@EnableJpaRepositories
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.weng.fsv.common"})
@EnableConfigurationProperties(FsvOptionProperties.class)
public class FsvCommonConfiguration {


    @PostConstruct
    public void init() {
        log.info("fsv common module is loaded successful! ");
    }
}
