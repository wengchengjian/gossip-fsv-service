package com.weng.fsv.model.fsv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.*;

/**
 *  可阅读,观看,分享的资源的分页实体
 * @author wengchengjian
 * @date 2023/8/3-16:59
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("fsv_chapter")
@Table(name = "fsv_chapter")
public class FsvChapter extends BaseEntity {

    /**
     * 所属fsvId
     */
    private Long fsvId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 章节序号
     */
    @OrderBy
    @com.baomidou.mybatisplus.annotation.OrderBy
    private Integer number;


}
