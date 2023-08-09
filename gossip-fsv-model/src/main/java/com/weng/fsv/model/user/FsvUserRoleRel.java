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
@TableName("fsv_user_role_rel")
@Table(name = "fsv_user_role_rel")
public class FsvUserRoleRel extends BaseEntity {
    private Long roleId;

    private Long userId;
}
