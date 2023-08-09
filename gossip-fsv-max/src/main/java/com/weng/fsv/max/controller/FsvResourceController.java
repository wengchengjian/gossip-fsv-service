package com.weng.fsv.max.controller;

import com.weng.fsv.model.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wengchengjian
 * @date 2023/8/4-9:38
 */
@Slf4j
@RestController
@RequestMapping("/api/fsv/resource")
public class FsvResourceController {



    @GetMapping("/preUpload")
    public Result<String> preUpload() {
        return Result.success();
    }
}
