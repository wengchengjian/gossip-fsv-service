package com.weng.fsv.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.fsv.model.user.FsvSecurityPermission;
import com.weng.fsv.model.user.FsvSecurityRole;
import com.weng.fsv.model.user.FsvSecurityUser;

import java.util.List;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:23
 */
public interface FsvUserService extends IService<FsvSecurityUser> {
    /**
     * 获取用户所有角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<FsvSecurityRole> getRoleList(Long userId);

    /**
     * 获取用户所有权限
     * @param userId 用户id
     * @return 权限列表
     */
    List<FsvSecurityPermission> getPermissionList(Long userId);

    /**
     * 校验用户密码
     * @param loginId
     * @param password
     * @return
     */
    boolean matchPassword(long loginId, String password);

    boolean matchPassword(String rawPassword, String encodedPassword);


    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    FsvSecurityUser findByName(String username);
}
