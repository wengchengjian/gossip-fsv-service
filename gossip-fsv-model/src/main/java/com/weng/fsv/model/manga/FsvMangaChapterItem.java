package com.weng.fsv.model.manga;

import com.baomidou.mybatisplus.annotation.TableName;
import com.weng.fsv.model.base.BaseLogicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author wengchengjian
 * @date 2023/8/3-17:33
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("fsv_manga_chapter_item")
@Table(name = "fsv_manga_chapter_item")
public class FsvMangaChapterItem extends BaseLogicEntity {

    /**
     * 所属Chapter
     */
    private Long chapterId;

    /**
     * 漫画章节图片序号
     */
    @OrderBy
    @com.baomidou.mybatisplus.annotation.OrderBy
    private Integer number;

    /**
     * 漫画章节图片地址
     */
    private String imgUrl;
}
