package com.weng.fsv.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.fsv.common.enums.FsvRoleEnum;
import com.weng.fsv.common.mapper.*;
import com.weng.fsv.common.service.FsvUserService;
import com.weng.fsv.common.support.PasswordEncoder;
import com.weng.fsv.model.base.Result;
import com.weng.fsv.model.user.*;
import com.weng.fsv.model.user.dto.EditUserDto;
import com.weng.fsv.utils.ValidateCodeUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public List<FsvSecurityRole> getRoleList(String username) {
        List<FsvSecurityRole> fsvSecurityRoles = getRoleNotFill(username);
        fsvSecurityRoles.forEach(fsvSecurityRole -> {
            List<Long> permissionIdList = fsvRolePermissionRelMapper.selectList(Wrappers.<FsvRolePermissionRel>lambdaQuery().eq(FsvRolePermissionRel::getRoleId, fsvSecurityRole.getId()))
                    .stream().map(FsvRolePermissionRel::getPermissionId).toList();

            fsvSecurityRole.setPermissionList(fsvSecurityPermissionMapper.selectBatchIds(permissionIdList));
        });
        return fsvSecurityRoles;
    }

    @Override
    public List<FsvSecurityRole> getRoleNotFill(String username) {
        FsvSecurityUser user = findByName(username);
        if(user == null) {
            return new ArrayList<>();
        }
        List<Long> roleIdList = fsvUserRoleRelMapper.selectList(Wrappers.<FsvUserRoleRel>lambdaQuery().eq(FsvUserRoleRel::getUserId, user.getId()))
                .stream().map(FsvUserRoleRel::getRoleId).toList();

        List<FsvSecurityRole> fsvSecurityRoles = fsvSecurityRoleMapper.selectBatchIds(roleIdList);
        return fsvSecurityRoles;
    }

    @Override
    public List<FsvSecurityPermission> getPermissionList(String username) {
        List<FsvSecurityRole> fsvSecurityRoles = getRoleNotFill(username);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCommonUser(FsvSecurityUser fsvSecurityUser) {
        save(fsvSecurityUser);

        // 给用户添加一个普通用户
        FsvUserRoleRel roleRel = FsvRoleEnum.Common.createRoleRel(fsvSecurityUser.getId());

        fsvUserRoleRelMapper.insert(roleRel);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeWithRoleBatchByIds(List<Long> userIdList) {
        removeBatchByIds(userIdList);
        fsvUserRoleRelMapper.delete(Wrappers.<FsvUserRoleRel>lambdaQuery().in(FsvUserRoleRel::getUserId, userIdList));
    }

    @Override
    public void editUser(FsvSecurityUser user, EditUserDto editUserDto) {
        if(StringUtils.hasText(editUserDto.getDescription())) {
            user.setDescription(editUserDto.getDescription());
        }
        if(StringUtils.hasText(editUserDto.getNickname())) {
            user.setNickname(editUserDto.getNickname());
        }
        if(StringUtils.hasText(editUserDto.getEmail())) {
            user.setEmail(editUserDto.getEmail());
        }
        if(StringUtils.hasText(editUserDto.getPhone())) {
            user.setPhone(editUserDto.getPhone());
        }
        if(StringUtils.hasText(editUserDto.getTitle())) {
            user.setTitle(editUserDto.getTitle());
        }
        if(StringUtils.hasText(editUserDto.getAvatar())) {
            user.setAvatar(editUserDto.getAvatar());
        }

        updateById(user);
    }

    @Override
    public FsvSecurityUser autoRegisterByEmail(String email) {
        FsvSecurityUser user = findByEmail(email);
        if(user != null) {
            return user;
        }
        FsvSecurityUser randomUser = generateUserByDefault();
        randomUser.setEmail(email);
        save(randomUser);
        return randomUser;
    }

    @Override
    public FsvSecurityUser findByEmail(String email) {
        return this.baseMapper.selectOne(Wrappers.<FsvSecurityUser>lambdaQuery().eq(FsvSecurityUser::getEmail, email));
    }

    @Override
    public FsvSecurityUser findByPhone(String phone) {
        return this.baseMapper.selectOne(Wrappers.<FsvSecurityUser>lambdaQuery().eq(FsvSecurityUser::getPhone, phone));
    }

    private FsvSecurityUser generateUserByDefault() {
        return FsvSecurityUser.builder()
                .username(ValidateCodeUtils.generateValidateCode(8))
                .nickname(ValidateCodeUtils.generateValidateCode(6))
                .password(passwordEncoder.encode(ValidateCodeUtils.generateValidateCode(8))).build();
    }
}
