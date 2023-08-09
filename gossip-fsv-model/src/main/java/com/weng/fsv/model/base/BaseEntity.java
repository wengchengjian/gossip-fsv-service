package com.weng.fsv.model.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.weng.fsv.model.group.UpdateGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:19
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -868372705835286486L;

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @Positive(message = "主键id只能为正数")
    @NotNull(message = "更新时id不能为空", groups = {UpdateGroup.class})
    protected Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Column(columnDefinition = "datetime default now() not null comment '创建时间'")
    protected LocalDateTime createTime;

    @OrderBy
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Column(columnDefinition = "datetime default now() not null comment '更新时间'")
    protected LocalDateTime updateTime;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected String createBy;

    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    @Version
    @Min(value = 0, message = "版本号字段最小为0")
    @JsonIgnore
    @Column(columnDefinition = "int default 0 comment '版本号'")
    protected Integer version;
}
