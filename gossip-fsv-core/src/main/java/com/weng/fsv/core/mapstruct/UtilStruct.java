package com.weng.fsv.core.mapstruct;

import com.weng.fsv.model.user.FsvSecurityUser;
import com.weng.fsv.model.user.dto.SaveUserDto;
import com.weng.fsv.model.user.vo.FsvUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wengchengjian
 * @date 2023/8/9-15:30
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtilStruct {

    @Mapping(target = "status", ignore = true)
    FsvSecurityUser dto2User(SaveUserDto userPo);

    FsvUserVo userToVo(FsvSecurityUser user);

}
