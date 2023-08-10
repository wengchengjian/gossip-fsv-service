package com.weng.fsv.core.security;

import cn.dev33.satoken.stp.StpInterface;
import com.weng.fsv.common.mapper.*;
import com.weng.fsv.common.service.FsvUserService;
import com.weng.fsv.model.user.FsvSecurityPermission;
import com.weng.fsv.model.user.FsvSecurityRole;
import com.weng.fsv.model.user.FsvSecurityUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:02
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private FsvUserService fsvUserService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<FsvSecurityPermission> permissionList = fsvUserService.getPermissionList((String) loginId);
        return permissionList.stream().map(FsvSecurityPermission::getCode).toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<FsvSecurityRole> roleList = fsvUserService.getRoleList((String) loginId);
        return roleList.stream().map(FsvSecurityRole::getName).toList();
    }
}
