package com.subscribe.mainp.service;

import com.subscribe.mainp.dto.UserDto;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, Integer userId);

    //delete
    void deleteUser(Integer userId);
    //getById
    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    Integer getUserIdByUsername(String username);

    Integer getUserIdByEmail(String email);

    Boolean updatePassword(String mail, String password);
}
