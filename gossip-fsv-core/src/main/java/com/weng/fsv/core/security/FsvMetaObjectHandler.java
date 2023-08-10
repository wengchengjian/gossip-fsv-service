package com.weng.fsv.core.security;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The type My meta object handler.
 *
 * @Author 翁丞健
 * @Date 2022 /3/31 23:38
 * @Version 1.0.0
 */
@Slf4j
@Component
public class FsvMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String username = StpUtil.getLoginId("system");
        this.strictInsertFill(metaObject, "createBy", String.class, username);
        this.strictInsertFill(metaObject, "updateBy", String.class, username);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "deleted", () -> 0, Integer.class);
        this.strictInsertFill(metaObject, "version", () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", null);
        metaObject.setValue("updateBy", null);
        String username = StpUtil.getLoginId("system");

        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateBy", String.class, username);
    }


}
