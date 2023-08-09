package com.weng.fsv.core.model.dto;

import lombok.Data;

/**
 * @author wengchengjian
 * @date 2023/8/9-15:25
 */
@Data
public class SaveUserDto {
    private String username;

    private String nickname;

    private String title;

    private String avatar;

    private String email;

    private String phone;

    private String password;

    private String description;
}
