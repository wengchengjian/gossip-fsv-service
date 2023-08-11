package com.weng.fsv.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseLogicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

/**
 * @author wengchengjian
 * @date 2023/8/3-16:54
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("fsv_security_user")
@Table(name = "fsv_security_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "deleted"}),
        @UniqueConstraint(columnNames = {"email", "deleted"}),
        @UniqueConstraint(columnNames = {"phone", "deleted"})})
public class FsvSecurityUser extends BaseLogicEntity {

    private String username;

    private String nickname;

    private String title;

    private String avatar;

    private String email;

    private String phone;

    private String password;

    private String description;

    @Column(columnDefinition = "bit(1) default 1 not null comment '账号状态'")
    private Integer status;
}
