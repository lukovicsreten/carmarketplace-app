package com.example.usedcars.service;

import com.example.usedcars.dto.request.UserRequestDto;
import com.example.usedcars.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    void deleteUser(Long id);

    List<UserResponseDto> searchByName(String name);

    UserResponseDto getUserByEmail(String email);
}
