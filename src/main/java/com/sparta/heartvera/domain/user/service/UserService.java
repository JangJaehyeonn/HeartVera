package com.sparta.heartvera.domain.user.service;

import com.sparta.heartvera.domain.user.dto.UserPwRequestDto;
import com.sparta.heartvera.domain.user.dto.UserResponseDto;
import com.sparta.heartvera.domain.user.dto.UserUpdateDto;
import com.sparta.heartvera.domain.user.entity.User;
import com.sparta.heartvera.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 사용자 프로필 조회
  public UserResponseDto getUser(Long userSeq) {
    User user = findByUserId(userSeq);
    UserResponseDto responseDto = new UserResponseDto(user);
    return responseDto;
  }

  // 사용자 프로필 수정
  @Transactional
  public UserUpdateDto updateUser(UserUpdateDto updateDto, Long userSeq) {
    User user = findByUserId(userSeq);
    user.updateUser(updateDto);
    UserUpdateDto responseDto = new UserUpdateDto(user);
    return responseDto;
  }

  // 비밀번호 변경
  @Transactional
  public void updatePassword(UserPwRequestDto requestDto, Long userSeq) {
    User user = findByUserId(userSeq);
    String password = requestDto.getPassword();
    String newPassword = requestDto.getNewPassword();

    if (!passwordEncoder.matches(password, user.getUserPassword())) {
      throw new IllegalArgumentException("현재 비밀번호와 사용자의 비밀번호가 일치하지 않습니다.");
    }

    if (passwordEncoder.matches(newPassword, user.getUserPassword())) {
      throw new IllegalArgumentException("동일한 비밀번호로는 변경할 수 없습니다.");
    }

    user.updatePassword(passwordEncoder.encode(newPassword));
  }

  private User findByUserId(Long userSeq) {
    return userRepository.findById(userSeq).orElseThrow(() ->
        new IllegalArgumentException("해당 사용자는 존재하지 않습니다.")
    );
  }

}
