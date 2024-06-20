package com.sparta.heartvera.domain.auth.controller;

import com.sparta.heartvera.domain.auth.dto.SignupRequestDto;
import com.sparta.heartvera.domain.auth.dto.TokenRequestDto;
import com.sparta.heartvera.domain.auth.dto.TokenResponseDto;
import com.sparta.heartvera.domain.auth.service.AuthService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import com.sparta.heartvera.security.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/refresh")
    public ResponseEntity<String> reAuth(@RequestBody TokenRequestDto requestDto, HttpServletResponse response) {
        String refreshtoken = requestDto.getRefreshToken();
        TokenResponseDto newToken = authService.reAuth(refreshtoken);
        // token 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, newToken.getAccessToken());

        return ResponseEntity.ok().body(newToken.getAccessToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute(JwtUtil.AUTHORIZATION_HEADER);
        return authService.logout(userDetails.getUser());
    }

}
