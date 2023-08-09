package com.weng.fsv.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseLogicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
@TableName("fsv_security_permission")
@Table(name = "fsv_security_permission")
public class FsvSecurityPermission extends BaseLogicEntity {
    private String code;

    private String name;
}
