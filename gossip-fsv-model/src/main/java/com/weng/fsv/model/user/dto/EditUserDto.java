package com.weng.fsv.model.user.dto;

import lombok.Data;

/**
 * @author wengchengjian
 * @date 2023/8/10-15:20
 */
@Data
public class EditUserDto {
    private String oldPassword;

    private String newPassword;

    private String nickname;

    private String title;

    private String avatar;

    private String email;

    private String phone;

    private String description;

}
