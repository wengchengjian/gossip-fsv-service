package com.weng.fsv.core.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.weng.fsv.common.enums.LogLevelEnum;
import com.weng.fsv.common.exception.UserExistException;
import com.weng.fsv.common.service.FsvUserService;
import com.weng.fsv.common.support.LoggingInterface;
import com.weng.fsv.common.support.PasswordEncoder;
import com.weng.fsv.core.mapstruct.UtilStruct;
import com.weng.fsv.model.base.Result;
import com.weng.fsv.model.user.FsvSecurityUser;
import com.weng.fsv.model.user.dto.EditUserDto;
import com.weng.fsv.model.user.dto.SaveUserDto;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wengchengjian
 * @date 2023/8/8-17:02
 */
@RestController
@RequestMapping("/api/fsv/user")
public class UserController implements LoggingInterface {

    @Resource
    private FsvUserService fsvUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UtilStruct utilStruct;


    @RequestMapping("doLogin")
    public Result<SaTokenInfo> doLogin(String username, String password) throws AccountException {
        FsvSecurityUser user =  fsvUserService.findByName(username);
        if(user == null) {
            throw new AccountException(username);
        }
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());

        if(fsvUserService.matchPassword(password, user.getPassword())) {
            StpUtil.login(username);
            return Result.success(StpUtil.getTokenInfo());
        }
        return Result.failure("登录失败");
    }

    @PostMapping("/save")
    public Result<SaTokenInfo> save(@RequestBody SaveUserDto saveUserDto) {

        if(!StringUtils.hasText(saveUserDto.getUsername())) {
            return Result.failure("用户名不能为空");
        }
        FsvSecurityUser oldUser = fsvUserService.findByName(saveUserDto.getUsername());
        if(oldUser != null) {
            throw new UserExistException(saveUserDto.getUsername());
        }
        FsvSecurityUser fsvSecurityUser = utilStruct.dto2User(saveUserDto);
        if(!StringUtils.hasText(fsvSecurityUser.getPassword())) {
            return Result.failure("密码不能为空");
        }
        //加密密码
        fsvSecurityUser.setPassword(passwordEncoder.encode(fsvSecurityUser.getPassword()));
        fsvUserService.saveCommonUser(fsvSecurityUser);

        // 自动登录
        StpUtil.login(fsvSecurityUser.getUsername());

        return Result.success(StpUtil.getTokenInfo());
    }

//    @SaCheckRole(value = {"Admin", "SuperAdmin"}, mode = SaMode.OR)
    @DeleteMapping("/delete/{ids:\\d+[,\\d]*}")
    public SaResult delete(@PathVariable("ids") String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).filter(StringUtils::hasText).map(Long::parseLong).toList();
        if(idList.contains(1L)) {
            return SaResult.error("不能删除超级管理员,无此权限");
        }
        if(idList.size() > 500) {
            return SaResult.error("每次最多删除500名");
        }
        List<FsvSecurityUser> fsvSecurityUsers = fsvUserService.listByIds(idList);
        if(!fsvSecurityUsers.isEmpty()){
            for (FsvSecurityUser fsvSecurityUser : fsvSecurityUsers) {
                // 踢下线用户
                StpUtil.kickout(fsvSecurityUser.getUsername());
            }
            fsvUserService.removeWithRoleBatchByIds(idList);
        }
        return SaResult.ok();
    }

    @PutMapping("/edit/{id}")
    public Result<Void> editUser(@PathVariable("id") Long id, @RequestBody EditUserDto editUserDto) {
        FsvSecurityUser user = fsvUserService.getById(id);

        if(user == null) {
            return Result.failure("没有这样的用户");
        }
        // 修改密码逻辑
        if(StringUtils.hasText(editUserDto.getOldPassword())) {
            if(StringUtils.hasText(editUserDto.getNewPassword())) {
                if(passwordEncoder.matches(editUserDto.getOldPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(editUserDto.getNewPassword()));
                }
            } else {
                return Result.failure("请输入新的密码");
            }
        }
        fsvUserService.editUser(user, editUserDto);
        return Result.success();
    }



    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public boolean isLogin() {
        return StpUtil.isLogin();
    }

    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @SaCheckLogin
    @PostMapping("/openSafe")
    public SaResult openSafe(String password) {
        String username = (String) StpUtil.getLoginId();
        FsvSecurityUser user = fsvUserService.findByName(username);

        if(fsvUserService.matchPassword(password, user.getPassword())) {
            StpUtil.openSafe(120);
            return SaResult.ok("二级认证成功");
        }
        return SaResult.error("二级认证失败");
    }

    @SaCheckRole("admin")
    @PostMapping("/ban")
    public SaResult ban(String loginId) {
        StpUtil.kickout(loginId);
        StpUtil.disable(loginId, 86400);
        return SaResult.ok("封禁用户成功");
    }

    private static final String MODULE = "user-manager";

    @Override
    public String getModule() {
        return MODULE;
    }

    @Override
    public LogLevelEnum getLogLevel() {
        return LogLevelEnum.API;
    }
}
