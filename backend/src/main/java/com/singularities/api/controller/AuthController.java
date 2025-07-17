package com.singularities.api.controller;

import com.singularities.api.data.entity.UserModel;
import com.singularities.api.dto.request.GenerateTokenRequestDto;
import com.singularities.api.dto.request.GetTokenRequestDto;
import com.singularities.api.dto.request.UserUpdateRequestDto;
import com.singularities.api.dto.response.UserResponseDto;
import com.singularities.api.mapper.UserMapper;
import com.singularities.api.service.AuthService;
import com.singularities.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto getMyInformation() {
        return userMapper.toDto(authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication()));
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto updateMyInformation(@Valid @RequestBody UserUpdateRequestDto form) {
        UserModel user = authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return userMapper.toDto(userService.update(user, form));
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public String token(@Valid @RequestBody GetTokenRequestDto getTokenRequestDto) {
        return authService.getToken(getTokenRequestDto);
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public void generate(@Valid @RequestBody GenerateTokenRequestDto generateTokenRequestDto) {
        authService.generateToken(generateTokenRequestDto);
    }
}