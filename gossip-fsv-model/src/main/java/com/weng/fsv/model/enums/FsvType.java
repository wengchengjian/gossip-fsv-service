package com.weng.fsv.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wengchengjian
 * @date 2023/8/3-17:18
 */
@Getter
@AllArgsConstructor
public enum FsvType implements IEnum<String> {
    NOVEL, VIDEO, MANGA;

    @Override
    public String getValue() {
        return this.name();
    }
}
