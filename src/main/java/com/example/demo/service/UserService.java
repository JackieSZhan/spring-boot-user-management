package com.example.demo.service;


import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    List<UserResponseDto> searchByEmail(String keyword);

    List<UserResponseDto> getActiveUsers(Boolean threshold);

    List<UserResponseDto> getInactiveUsers(Boolean threshold);

    void deleteUser(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto request);


}

