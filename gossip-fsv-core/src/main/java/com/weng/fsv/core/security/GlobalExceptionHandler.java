package com.weng.fsv.core.security;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wengchengjian
 * @date 2023/8/9-11:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        log.error("{}", e);
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler
    public SaResult handleDisableException(DisableServiceException e) {
        log.error("{}", e);
        return SaResult.error(e.getMessage());
    }
}
