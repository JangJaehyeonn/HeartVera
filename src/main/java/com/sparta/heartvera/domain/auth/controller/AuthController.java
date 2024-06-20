package com.sparta.heartvera.domain.auth.controller;

import com.sparta.heartvera.domain.auth.dto.LoginRequestDto;
import com.sparta.heartvera.domain.auth.dto.SignupRequestDto;
import com.sparta.heartvera.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입",description = "회원가입")
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto requestDto){
        return authService.signup(requestDto);
    }

}
