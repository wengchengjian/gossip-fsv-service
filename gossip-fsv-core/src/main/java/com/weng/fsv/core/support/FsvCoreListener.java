package com.weng.fsv.core.support;

import com.weng.fsv.core.support.job.AuditLogHandleTask;
import com.weng.fsv.model.fsv.FsvAuditLog;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wengchengjian
 * @date 2023/8/11-9:53
 */
@Slf4j
@Component
public class FsvCoreListener {

    @Resource
    private AuditLogHandleTask auditLogHandleTask;

    @Async
    @EventListener(AuditLogEvent.class)
    public void listenToAuditLog(AuditLogEvent event) {
        FsvAuditLog auditLog = event.getAuditLog();
        log.info("ip:{}, 用户:{}, 路径:{}, 模块:{}, 方法:{}, success:{}, 耗时:{}ms",auditLog.getIpAddr(),auditLog.getUsername(),auditLog.getPath(),auditLog.getModule(),auditLog.getMethod(),auditLog.isSuccess(),auditLog.getConsumeTime());
        auditLogHandleTask.addLogToQueue(auditLog);
    }
}
