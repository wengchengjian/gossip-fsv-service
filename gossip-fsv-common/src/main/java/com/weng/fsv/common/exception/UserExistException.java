package com.weng.fsv.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author wengchengjian
 * @date 2023/8/10-13:42
 */
@Getter
@AllArgsConstructor
public class UserExistException extends RuntimeException{
    private String username;

    public UserExistException(String username,String message) {
        super(message);
        this.username = username;
    }

}
