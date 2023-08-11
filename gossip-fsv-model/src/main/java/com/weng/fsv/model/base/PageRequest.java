package com.weng.fsv.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author wengchengjian
 */
@Data
public class PageRequest {

    @Transient
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Integer pageSize = 5;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    protected Integer current = 0;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    protected String orderBy = "create_time";

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    protected boolean orderDesc = true;

    /**
     * 提交接口时若带有此UUID则代表不启用缓存
     */
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    protected String uuid;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    protected LocalDateTime startTime = LocalDateTime.now().minusDays(7);

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    @Transient
    protected LocalDateTime endTime = LocalDateTime.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    @Transient
    protected String keyword;
}
