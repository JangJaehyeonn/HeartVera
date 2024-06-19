package com.sparta.heartvera.domain.user.controller;

import com.sparta.heartvera.domain.user.dto.UserPwRequestDto;
import com.sparta.heartvera.domain.user.dto.UserResponseDto;
import com.sparta.heartvera.domain.user.dto.UserUpdateDto;
import com.sparta.heartvera.domain.user.service.UserService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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
  public UserResponseDto getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    UserResponseDto responseDto = userService.getUser(userDetails.getUser().getUserSeq());
    return responseDto;
  }

  // 사용자의 프로필 수정
  @PutMapping("/users")
  public UserUpdateDto updateUser(@RequestBody @Valid UserUpdateDto updateDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return userService.updateUser(updateDto, userDetails.getUser().getUserSeq());
  }

  // 비밀번호 변경
  @PutMapping("/users/password")
  public ResponseEntity<Object> updatePassword(@RequestBody @Valid UserPwRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    userService.updatePassword(requestDto, userDetails.getUser().getUserSeq());
    return ResponseEntity.ok()
        .body(Map.of("statusCode",200,"message","비밀번호가 변경되었습니다."));
  }
}
