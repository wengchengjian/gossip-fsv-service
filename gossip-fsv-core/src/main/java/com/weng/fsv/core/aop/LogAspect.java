package com.weng.fsv.core.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.weng.fsv.common.support.LoggingInterface;
import com.weng.fsv.core.support.AuditLogEvent;
import com.weng.fsv.model.fsv.FsvAuditLog;
import com.weng.fsv.utils.HttpClientUtil;
import com.weng.fsv.utils.IpUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jodd.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 用于记录接口审计日志的切面
 * @author wengchengjian
 * @date 2023/8/11-9:21
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "fsv.options", value = "logging", havingValue = "true",matchIfMissing = true)
public class LogAspect implements ApplicationEventPublisherAware {
    @Pointcut("execution(* com.weng.fsv..*.*Controller.*(..))")
    public void point() {}

    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void init() {
        log.info("审计日志切面初始化...");
    }


    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String username = StpUtil.getLoginId("tourist");
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = request.getRequestURI();
        String module = checkRequireModule(joinPoint);
        Signature signature = joinPoint.getSignature();
        String ip = IpUtils.getIpAddr();
        String name = signature.getName();
        Object[] args = joinPoint.getArgs();

        FsvAuditLog.FsvAuditLogBuilder auditLogBuilder = FsvAuditLog.builder().ipAddr(ip)
                .args(Arrays.toString(args)).username(username).module(module).method(name).path(url);
        Object result = null;
        Throwable ex = null;
        long start = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            auditLogBuilder.success(false);
            auditLogBuilder.exception(ExceptionUtil.exceptionChainToString(e));
            ex =  e;
        }
        auditLogBuilder.consumeTime(System.currentTimeMillis() - start);
        // 发布审计事件
        if(eventPublisher != null) {
            eventPublisher.publishEvent(new AuditLogEvent(auditLogBuilder.build()));
        }
        if (ex != null) {
            throw ex;
        }
        return result;
    }

    private String checkRequireModule(ProceedingJoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        if(target instanceof LoggingInterface logger) {
            return logger.getModule();
        } else {
            return "default";
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        eventPublisher = applicationEventPublisher;
    }
}
