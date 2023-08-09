package com.weng.fsv.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseLogicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.List;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:10
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("fsv_security_role")
@Table(name = "fsv_security_role")
public class FsvSecurityRole extends BaseLogicEntity {

    private String name;

    private String description;

    @Transient
    @TableField(exist = false)
    private List<FsvSecurityPermission> permissionList;
}
