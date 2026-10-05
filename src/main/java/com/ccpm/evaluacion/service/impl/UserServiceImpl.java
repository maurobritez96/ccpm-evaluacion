package com.ccpm.evaluacion.service.impl;

import com.ccpm.evaluacion.dto.PhoneDto;
import com.ccpm.evaluacion.dto.UserRequestDto;
import com.ccpm.evaluacion.dto.UserResponseDto;
import com.ccpm.evaluacion.exception.UserAlreadyExistsException;
import com.ccpm.evaluacion.model.Phone;
import com.ccpm.evaluacion.model.User;
import com.ccpm.evaluacion.repository.UserRepository;
import com.ccpm.evaluacion.security.JwtService;
import com.ccpm.evaluacion.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new UserAlreadyExistsException("El correo ya registrado");
        }
        String token = jwtService.generateToken(requestDto.email());

        List<Phone> phones = requestDto.phones() != null
                ? requestDto.phones().stream()
                .map(phoneDto -> Phone.builder()
                        .number(phoneDto.number())
                        .cityCode(phoneDto.citycode())
                        .contryCode(phoneDto.contrycode())
                        .build())
                .toList()
                : List.of();

        User user = User.builder()
                .name(requestDto.name())
                .email(requestDto.email())
                .password(requestDto.password())
                .token(token)
                .phones(phones)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
    }

    // funciones privadas
    private UserResponseDto mapToDto(User user) {
        List<PhoneDto> phoneDtos = user.getPhones() != null
                ? user.getPhones().stream()
                  .map(phone -> new PhoneDto(
                          phone.getNumber(),
                          phone.getCityCode(),
                          phone.getContryCode()
                  ))
                  .toList()
                : List.of();

        return new UserResponseDto(
                user.getId(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken(),
                user.isActive(),
                user.getName(),
                user.getEmail(),
                phoneDtos
        );
    }
}
