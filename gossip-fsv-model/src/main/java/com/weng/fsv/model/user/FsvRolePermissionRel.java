package com.weng.fsv.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseEntity;
import com.weng.fsv.model.base.BaseLogicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:15
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("fsv_role_permission_rel")
@Table(name = "fsv_role_permission_rel")
public class FsvRolePermissionRel extends BaseEntity {
    private Long roleId;

    private Long permissionId;
}
