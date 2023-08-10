package com.weng.fsv.common.enums;

import com.weng.fsv.model.user.FsvSecurityRole;
import com.weng.fsv.model.user.FsvUserRoleRel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wengchengjian
 * @date 2023/8/10-11:04
 */
@Getter
@AllArgsConstructor
public enum FsvRoleEnum {
    Common(3L,"普通用户"),
    Admin(2L,"管理员"),
    SuperAdmin(1L,"超级管理员"),
    Tourist(4L,"游客");

    final Long id;
    final String description;

    public FsvUserRoleRel createRoleRel(Long userId) {
        return FsvUserRoleRel.builder().userId(userId).roleId(getId()).build();
    }

}
