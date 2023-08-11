package com.weng.fsv.core.security.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.listener.SaTokenListenerForSimple;
import cn.dev33.satoken.stp.SaLoginModel;
import org.springframework.stereotype.Component;

/**
 * @author wengchengjian
 * @date 2023/8/9-15:44
 */
@Component
public class FsvSaTokenListener extends SaTokenListenerForSimple {
    /** 每次登录时触发 */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
//        System.out.println("---------- 自定义侦听器实现 doLogin");
    }
}
