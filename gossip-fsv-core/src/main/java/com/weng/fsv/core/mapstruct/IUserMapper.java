package com.weng.fsv.core.mapstruct;

import com.weng.fsv.core.model.dto.SaveUserDto;
import com.weng.fsv.model.user.FsvSecurityUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wengchengjian
 * @date 2023/8/9-15:30
 */
@Mapper
public interface IUserMapper {
    IUserMapper INSTANCT = Mappers.getMapper(IUserMapper.class);

    FsvSecurityUser dto2entity(SaveUserDto userPo);

}
