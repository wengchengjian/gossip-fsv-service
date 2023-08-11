package com.weng.fsv.core.support.job;

import cn.hutool.cron.task.Task;
import com.weng.fsv.common.service.FsvAuditLogService;
import com.weng.fsv.core.support.AuditLogEvent;
import com.weng.fsv.model.fsv.FsvAuditLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wengchengjian
 * @date 2023/8/11-10:29
 */
@Component
public class AuditLogHandleTask implements Task {
    public static final BlockingQueue<FsvAuditLog> queue = new LinkedBlockingQueue<>(1000);

    @Resource
    private FsvAuditLogService fsvAuditLogService;

    @Override
    public void execute() {
        if(!queue.isEmpty()) {
            fsvAuditLogService.saveBatch(queue);
            queue.clear();
        }
    }

    public void addLogToQueue(FsvAuditLog log) {
        queue.offer(log);
    }

}
