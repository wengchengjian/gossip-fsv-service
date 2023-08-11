package com.weng.fsv.model.fsv.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseLogicEntity;
import com.weng.fsv.model.base.PageRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author wengchengjian
 * @date 2023/8/11-9:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FsvAuditLogPageDto extends PageRequest {
    private String username;

    private String ipAddr;

    private String method;

    private String module;

    private String path;

    private String args;
    /**
     * 接口耗时 单位ms
     */
    private Long consumeTime;

    private Boolean success;
}
