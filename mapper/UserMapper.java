package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);
}
