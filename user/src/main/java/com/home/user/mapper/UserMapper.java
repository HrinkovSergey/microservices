package com.home.user.mapper;

import com.home.user.domain.User;
import com.home.user.dto.UserDto;
import com.home.user.dto.UserForCreatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserForCreatingDto userForCreatingDto);

    @Mapping(target = "location", ignore = true)
    @Mapping(target = "company", ignore = true)
    UserDto toDto(User user);
}
