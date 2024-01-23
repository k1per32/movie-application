package com.k1per32.movieapplication.mapper;


import com.k1per32.movieapplication.dto.UserDto;

import com.k1per32.movieapplication.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convertToUser(UserDto userDto);
    UserDto convertToUserDto(User user);
}
