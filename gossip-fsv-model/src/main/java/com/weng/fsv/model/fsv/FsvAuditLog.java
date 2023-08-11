package com.weng.fsv.model.fsv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseLogicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author wengchengjian
 * @date 2023/8/11-9:16
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("fsv_audit_log")
@Entity
@Table(name = "fsv_audit_log")
public class FsvAuditLog extends BaseLogicEntity {
    private String username;

    private String ipAddr;

    private String method;

    private String module;

    private String path;

    private String args;

    /**
     * 接口耗时 单位ms
     */
    private long consumeTime;

    @Builder.Default
    private boolean success = true;

    @Column(columnDefinition = "text comment '异常信息'")
    private String exception;
}
