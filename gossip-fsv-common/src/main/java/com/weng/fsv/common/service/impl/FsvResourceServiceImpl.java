package com.weng.fsv.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.fsv.common.mapper.FsvChapterMapper;
import com.weng.fsv.common.mapper.FsvResourceMapper;
import com.weng.fsv.common.service.FsvChapterService;
import com.weng.fsv.common.service.FsvResourceService;
import com.weng.fsv.model.fsv.FsvChapter;
import com.weng.fsv.model.fsv.FsvResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wengchengjian
 * @date 2023/8/4-9:30
 */
@Slf4j
@Service
public class FsvResourceServiceImpl extends ServiceImpl<FsvResourceMapper, FsvResource> implements FsvResourceService {

}
