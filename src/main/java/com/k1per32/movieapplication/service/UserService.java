package com.k1per32.movieapplication.service;

import com.k1per32.movieapplication.dto.RequestRegisterDto;
import com.k1per32.movieapplication.dto.UserDto;
import com.k1per32.movieapplication.dto.UserDtoWithoutEmail;

import java.util.List;

public interface UserService {
     List<UserDto> getAllUsers();

     UserDto getUsersById(int id) ;

    UserDto changeUser(int id, UserDtoWithoutEmail userDto);


    UserDto signUp(RequestRegisterDto requestRegisterDto);

    void deleteUser(int id);


}
