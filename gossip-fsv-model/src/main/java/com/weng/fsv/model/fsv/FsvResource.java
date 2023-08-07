package com.weng.fsv.model.fsv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseEntity;
import com.weng.fsv.model.enums.FsvType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 一种可阅读,观看,分享的资源
 * @author wengchengjian
 * @date 2023/8/3-17:15
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("fsv_resource")
@Table(name = "fsv_resource")
public class FsvResource extends BaseEntity {
    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 漫画图片地址
     */
    private String imgUrl;

    /**
     * 作者
     */
    private String author;

    /**
     * 分类
     */
    private String category;

    /**
     * 转载链接
     */
    private String reprintUrl;


    @Enumerated(EnumType.STRING)
    private FsvType fsvType;

}
