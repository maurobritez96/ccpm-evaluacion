package com.ccpm.evaluacion.service;

import com.ccpm.evaluacion.dto.UserRequestDto;
import com.ccpm.evaluacion.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto createUser(UserRequestDto requestDto);
    void deleteUser(Long id);
}
