package com.weng.fsv.max.controller;

import com.weng.fsv.model.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wengchengjian
 * @date 2023/8/4-9:38
 */
@RestController
@RequestMapping("/api/fsv/resource")
public class FsvResourceController {



    @GetMapping("/preUpload")
    public Result<String> preUpload() {
        return Result.success();
    }
}
