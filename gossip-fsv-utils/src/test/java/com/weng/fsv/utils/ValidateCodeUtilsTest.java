package com.weng.fsv.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wengchengjian
 * @date 2023/8/11-14:17
 */
@Slf4j
public class ValidateCodeUtilsTest {

    @Test
    void generateValidateCode() {
        int num = 6;
        for (int i = 0; i < 10; i++) {
            log.info(ValidateCodeUtils.generateValidateCode(num));
        }
    }
}