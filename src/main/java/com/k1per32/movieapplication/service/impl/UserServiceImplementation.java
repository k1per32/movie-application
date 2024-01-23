package com.k1per32.movieapplication.service.impl;


import com.k1per32.movieapplication.dto.RequestRegisterDto;
import com.k1per32.movieapplication.dto.UserDto;
import com.k1per32.movieapplication.dto.UserDtoWithoutEmail;
import com.k1per32.movieapplication.entity.Authority;
import com.k1per32.movieapplication.entity.User;
import com.k1per32.movieapplication.exception.AlreadyExistException;
import com.k1per32.movieapplication.mapper.UserMapper;
import com.k1per32.movieapplication.repository.AuthorityRepository;
import com.k1per32.movieapplication.repository.UserRepository;
import com.k1per32.movieapplication.service.UserService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Data
@Log4j2
public class UserServiceImplementation implements UserService {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String LATIN_PATTERN = "[a-zA-Z]+";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthorityRepository authorityRepository;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Получаем данные всех пользователей");
        List<User> userListEntity = userRepository.findAll();
        if (userListEntity.isEmpty()) {
            throw new RuntimeException();
        }
        log.info("Получение данные всех пользователей завершено");
        return userListEntity.stream().map(userMapper::convertToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUsersById(int id) {
        log.info("Поиск пользователя по id");
        User userEntity = userRepository.findById(id)
                .orElseThrow();
        log.info("Поиск пользователя по id завершено");
        return userMapper.convertToUserDto(userEntity);
    }

    @Override
    public UserDto changeUser(int id, UserDtoWithoutEmail userDto) {
        log.info("Изменение данных пользователя");
        User userEntity = userRepository.findById(id).orElseThrow();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setName(userDto.getName());
        userRepository.save(userEntity);
        log.info("Изменение данных пользователя завершено");

        return userMapper.convertToUserDto(userEntity);
    }

    @Override
    public UserDto signUp(RequestRegisterDto requestRegisterDto) {
        log.info("Регистрация пользователя");
        User user = new User();
        if(userRepository.findByEmail(requestRegisterDto.getEmail()).isPresent()){
            throw new AlreadyExistException("email уже используется");
        }
        user.setEmail(requestRegisterDto.getEmail());
        user.setName(requestRegisterDto.getName());
        user.setUsername(requestRegisterDto.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(requestRegisterDto.getPassword());
        user.setPassword(hashedPassword);
        user.setEnabled(true);
        Authority authority = new Authority();
        userRepository.save(user);
        authority.setAuthority("ROLE_USER");
        authority.setUsername(user.getUsername());
        authorityRepository.save(authority);
        log.info("Регистрация пользователя завершена");
        return userMapper.convertToUserDto(user);
    }


    @Override
    public void deleteUser(int id) {
        log.info("Удаление пользователя");
        userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);
        log.info("Удаление пользователя завершено");
    }
}
