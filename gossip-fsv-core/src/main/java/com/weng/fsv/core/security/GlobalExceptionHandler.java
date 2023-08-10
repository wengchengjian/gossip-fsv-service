package com.weng.fsv.core.security;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.util.SaResult;
import com.weng.fsv.common.enums.LogLevelEnum;
import com.weng.fsv.common.exception.UserExistException;
import com.weng.fsv.common.support.LoggingInterface;
import com.weng.fsv.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements LoggingInterface {
    private static final String MODULE = "全局异常";

    // 全局异常拦截
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        log.error("{}", e);
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler
    public SaResult handleDisableException(DisableServiceException e) {
        info("账号被封禁,ip:{},用户id:{},账号类型:{},服务:{},等级:{},剩余时间:{}", IpUtils.getIpAddr(), e.getLoginId(),e.getLoginType(),e.getService(),e.getLevel(), e.getDisableTime());
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler
    public SaResult handleAccountException(AccountException e) {
        info("用户:{},在ip:{}登录失败", e.getMessage(), IpUtils.getIpAddr());
        return SaResult.error("用户名或密码错误");
    }

    @ExceptionHandler
    public SaResult handleUserExistException(UserExistException e) {
        return SaResult.error("用户名:"+ e.getUsername() + "已存在");
    }


    @Override
    public String getModule() {
        return MODULE;
    }

    @Override
    public LogLevelEnum getLogLevel() {
        return LogLevelEnum.EXCEPTION;
    }
}
