package com.sparta.heartvera.domain.auth.controller;

import com.sparta.heartvera.domain.auth.dto.LoginRequestDto;
import com.sparta.heartvera.domain.auth.dto.SignupRequestDto;
import com.sparta.heartvera.domain.auth.dto.TokenRequestDto;
import com.sparta.heartvera.domain.auth.dto.TokenResponseDto;
import com.sparta.heartvera.domain.auth.service.AuthService;
import com.sparta.heartvera.domain.user.entity.UserRoleEnum;
import com.sparta.heartvera.security.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증 API",description = "회원가입, 로그인, 토큰 재발급을 담당하는 api입니다. 사용자의 정보가 없어도 접근 가능합니다.")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입",description = "ID, PW, 닉네임, 이메일, 한줄소개, 어드민 비밀번호(선택사항)를 기반으로 회원가입합니다.")
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto requestDto){
        UserRoleEnum authority = authService.signup(requestDto).getAuthority();
        if (authority == UserRoleEnum.ADMIN) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("어드민으로 회원가입이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("일반 사용자로 회원가입이 성공적으로 완료되었습니다.");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자가 로그인하고 JWT를 발급받습니다.")
    public TokenResponseDto login(@RequestBody LoginRequestDto request) {
        // swagger 작성을 위해 구현된 공간
        return null;
    }

    @Operation(summary = "토큰 재발급",description = "액세스 토큰, 리프래쉬 토큰을 재발급합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<String> reAuth(@RequestBody TokenRequestDto requestDto, HttpServletResponse response) {
        String refreshToken = requestDto.getRefreshToken();
        TokenResponseDto newToken = authService.reAuth(refreshToken);

        // access token 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, newToken.getAccessToken());

        return ResponseEntity.ok().body(newToken.getAccessToken());
    }

}
