package com.example.demo.service;


import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto request);

    List<UserResponseDto> createUsers(@Valid List<UserRequestDto> request);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto searchByEmail(String keyword);

    List<UserResponseDto> getUsersByStatus(Boolean threshold);

    UserResponseDto updateUser(Long id, UserRequestDto request);

    UserResponseDto updateUserStatus(Long id, Boolean active);

    void deleteUser(Long id);


}

