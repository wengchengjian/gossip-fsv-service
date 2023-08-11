package com.weng.fsv.common.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.fsv.common.mapper.FsvAuditLogMapper;
import com.weng.fsv.common.service.FsvAuditLogService;
import com.weng.fsv.model.fsv.FsvAuditLog;
import org.springframework.stereotype.Service;

/**
 * @author wengchengjian
 * @date 2023/8/11-9:18
 */
@Service
public class FsvAuditLogServiceImpl extends ServiceImpl<FsvAuditLogMapper,FsvAuditLog> implements FsvAuditLogService {
}
