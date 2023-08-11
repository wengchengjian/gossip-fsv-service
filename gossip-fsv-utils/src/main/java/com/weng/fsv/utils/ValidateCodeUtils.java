package com.weng.fsv.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wengchengjian
 * @date 2023/8/11-14:12
 */
public class ValidateCodeUtils {
    private static final String CODE = "1234567890abcdefghijklmnopqrstuvwxwzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static String generateValidateCode(int num) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            int pos = RANDOM.nextInt(CODE.length());
            builder.append(CODE.charAt(pos));
        }
        return builder.toString();
    }
}
