package com.sparta.heartvera.domain.user.controller;

import com.sparta.heartvera.domain.user.dto.UserPwRequestDto;
import com.sparta.heartvera.domain.user.dto.UserRequestDto;
import com.sparta.heartvera.domain.user.dto.UserResponseDto;
import com.sparta.heartvera.domain.user.service.UserService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import com.sparta.heartvera.security.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자의 프로필 조회
    @GetMapping("/users")
    public ResponseEntity<UserResponseDto> getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUser(userDetails.getUser().getUserSeq()));
    }

    // 사용자의 프로필 수정
    @PutMapping("/users")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(requestDto, userDetails.getUser().getUserSeq()));
    }

    // 비밀번호 변경
    @PutMapping("/users/password")
    public ResponseEntity updatePassword(@RequestBody @Valid UserPwRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(requestDto, userDetails.getUser().getUserSeq());
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경되었습니다.");
    }

    // 로그아웃
    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request, HttpServletResponse response) {
        // 헤더에 남아있는 정보 지우기
        request.removeAttribute(JwtUtil.AUTHORIZATION_HEADER);
        userService.logout(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃이 완료되었습니다.");
    }
}
