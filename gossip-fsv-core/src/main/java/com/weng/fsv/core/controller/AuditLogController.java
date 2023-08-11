package com.weng.fsv.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weng.fsv.model.base.Result;
import com.weng.fsv.model.fsv.FsvAuditLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wengchengjian
 * @date 2023/8/11-11:11
 */
@RestController
@RequestMapping("/api/fsv/auditLog")
public class AuditLogController {

    @GetMapping("/pageQuery")
    public Result<Page<FsvAuditLog>> pageQuery() {
        return Result.success();
    }

}
