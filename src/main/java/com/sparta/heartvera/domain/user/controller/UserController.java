package com.sparta.heartvera.domain.user.controller;

import com.sparta.heartvera.domain.user.dto.UserPwRequestDto;
import com.sparta.heartvera.domain.user.dto.UserRequestDto;
import com.sparta.heartvera.domain.user.dto.UserResponseDto;
import com.sparta.heartvera.domain.user.service.UserService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
