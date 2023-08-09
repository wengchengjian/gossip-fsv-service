package com.weng.fsv.core.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.weng.fsv.common.service.FsvUserService;
import com.weng.fsv.common.support.PasswordEncoder;
import com.weng.fsv.core.mapstruct.IUserMapper;
import com.weng.fsv.core.model.dto.SaveUserDto;
import com.weng.fsv.model.base.Result;
import com.weng.fsv.model.user.FsvSecurityUser;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wengchengjian
 * @date 2023/8/8-17:02
 */
@RestController
@RequestMapping("/api/fsv/user")
public class UserController {

    @Resource
    private FsvUserService fsvUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @RequestMapping("doLogin")
    public Result<SaTokenInfo> doLogin(String username, String password) {
        FsvSecurityUser user =  fsvUserService.findByName(username);
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());

        if(fsvUserService.matchPassword(password, user.getPassword())) {
            StpUtil.login(10001);
            return Result.success(StpUtil.getTokenInfo());
        }
        return Result.failure("登录失败");
    }

    @PostMapping("/save")
    public Result<SaTokenInfo> save(SaveUserDto saveUserDto) {

        FsvSecurityUser fsvSecurityUser = IUserMapper.INSTANCT.dto2entity(saveUserDto);
        fsvSecurityUser.setPassword(passwordEncoder.encode(fsvSecurityUser.getPassword()));
        fsvUserService.save(fsvSecurityUser);
        // 自动登录
        StpUtil.login(fsvSecurityUser.getId());

        return Result.success(StpUtil.getTokenInfo());
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
        long loginId = (long) StpUtil.getLoginId();

        if(fsvUserService.matchPassword(loginId, password)) {
            StpUtil.openSafe(120);
            return SaResult.ok("二级认证成功");
        }
        return SaResult.error("二级认证失败");
    }

    @SaCheckRole("admin")
    @PostMapping("/ban")
    public SaResult ban(Long loginId) {
        StpUtil.kickout(loginId);
        StpUtil.disable(loginId, 86400);
        return SaResult.ok("封禁用户成功");
    }

}
