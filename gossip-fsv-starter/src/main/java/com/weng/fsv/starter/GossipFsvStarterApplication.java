package com.weng.fsv.starter;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wengchengjian
 * @date 2023/8/3-16:41
 */
@Slf4j
@SpringBootApplication
public class GossipFsvStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(GossipFsvStarterApplication.class, args);
        log.info("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
