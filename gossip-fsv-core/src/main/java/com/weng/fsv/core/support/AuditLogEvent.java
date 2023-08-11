package com.weng.fsv.core.support;

import com.weng.fsv.model.fsv.FsvAuditLog;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 审计日志事件
 * @author wengchengjian
 * @date 2023/8/11-9:48
 */
public class AuditLogEvent extends ApplicationEvent {

    private FsvAuditLog auditLog;

    public AuditLogEvent(FsvAuditLog auditLog) {
        super(auditLog);
        this.auditLog = auditLog;
    }

    public FsvAuditLog getAuditLog() {
        return auditLog;
    }
}
