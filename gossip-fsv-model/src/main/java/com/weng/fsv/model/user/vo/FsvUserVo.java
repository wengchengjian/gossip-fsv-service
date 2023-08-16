package com.weng.fsv.model.user.vo;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FsvUserVo {
    private String username;

    private String nickname;

    private String title;

    private String avatar;

    private String email;

    private String phone;

    private String description;

}
