package com.weng.fsv.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.fsv.common.mapper.*;
import com.weng.fsv.common.service.FsvUserService;
import com.weng.fsv.common.support.PasswordEncoder;
import com.weng.fsv.model.user.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:23
 */
@Service
public class FsvUserServiceImpl extends ServiceImpl<FsvSecurityUserMapper, FsvSecurityUser> implements FsvUserService {

    @Resource
    private FsvUserRoleRelMapper fsvUserRoleRelMapper;

    @Resource
    private FsvSecurityPermissionMapper fsvSecurityPermissionMapper;

    @Resource
    private FsvSecurityRoleMapper fsvSecurityRoleMapper;

    @Resource
    private FsvRolePermissionRelMapper fsvRolePermissionRelMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public List<FsvSecurityRole> getRoleList(Long userId) {
        List<Long> roleIdList = fsvUserRoleRelMapper.selectList(Wrappers.<FsvUserRoleRel>lambdaQuery().eq(FsvUserRoleRel::getUserId, userId))
                .stream().map(FsvUserRoleRel::getRoleId).toList();

        List<FsvSecurityRole> fsvSecurityRoles = fsvSecurityRoleMapper.selectBatchIds(roleIdList);
        fsvSecurityRoles.forEach(fsvSecurityRole -> {
            List<Long> permissionIdList = fsvRolePermissionRelMapper.selectList(Wrappers.<FsvRolePermissionRel>lambdaQuery().eq(FsvRolePermissionRel::getRoleId, fsvSecurityRole.getId()))
                    .stream().map(FsvRolePermissionRel::getPermissionId).toList();

            fsvSecurityRole.setPermissionList(fsvSecurityPermissionMapper.selectBatchIds(permissionIdList));
        });
        return fsvSecurityRoles;
    }

    @Override
    public List<FsvSecurityPermission> getPermissionList(Long userId) {
        List<Long> roleIdList = fsvUserRoleRelMapper.selectList(Wrappers.<FsvUserRoleRel>lambdaQuery().eq(FsvUserRoleRel::getUserId, userId))
                .stream().map(FsvUserRoleRel::getRoleId).toList();

        List<FsvSecurityRole> fsvSecurityRoles = fsvSecurityRoleMapper.selectBatchIds(roleIdList);
        Set<FsvSecurityPermission> result = new HashSet<>();

        fsvSecurityRoles.forEach(fsvSecurityRole -> {
            List<Long> permissionIdList = fsvRolePermissionRelMapper.selectList(Wrappers.<FsvRolePermissionRel>lambdaQuery().eq(FsvRolePermissionRel::getRoleId, fsvSecurityRole.getId()))
                    .stream().map(FsvRolePermissionRel::getPermissionId).toList();

            result.addAll(fsvSecurityPermissionMapper.selectBatchIds(permissionIdList));
        });
        return result.stream().toList();
    }

    @Override
    public boolean matchPassword(long loginId, String password) {
        FsvSecurityUser fsvSecurityUser = this.baseMapper.selectById(loginId);
        return passwordEncoder.matches(password, fsvSecurityUser.getPassword());
    }

    @Override
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public FsvSecurityUser findByName(String username) {
        return this.baseMapper.selectOne(Wrappers.<FsvSecurityUser>lambdaQuery().eq(FsvSecurityUser::getUsername, username));
    }
}
