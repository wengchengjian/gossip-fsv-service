package com.weng.fsv.core.support;

import com.weng.fsv.core.support.job.AuditLogHandleTask;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wengchengjian
 * @date 2023/8/11-10:32
 */
@Component
public class FsvCoreSchedule {
    @Resource
    private AuditLogHandleTask auditLogHandleTask;


    /** 每分钟消费一次日志 */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void logCosume() {
        auditLogHandleTask.execute();
    }
}
