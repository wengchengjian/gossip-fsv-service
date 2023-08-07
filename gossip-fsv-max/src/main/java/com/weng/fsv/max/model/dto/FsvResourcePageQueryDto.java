package com.weng.fsv.max.model.dto;

import com.weng.fsv.model.base.PageRequest;
import com.weng.fsv.model.enums.FsvType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wengchengjian
 * @date 2023/8/4-9:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FsvResourcePageQueryDto extends PageRequest {

    private FsvType fsvType;

}
