package com.sparta.heartvera.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.sparta.heartvera.common.exception.CustomException;
import com.sparta.heartvera.common.exception.ErrorCode;
import com.sparta.heartvera.domain.user.dto.UserPwRequestDto;
import com.sparta.heartvera.domain.user.dto.UserRequestDto;
import com.sparta.heartvera.domain.user.dto.UserResponseDto;
import com.sparta.heartvera.domain.user.entity.PasswordHistory;
import com.sparta.heartvera.domain.user.entity.User;
import com.sparta.heartvera.domain.user.entity.UserRoleEnum;
import com.sparta.heartvera.domain.user.repository.PasswordHistoryRepository;
import com.sparta.heartvera.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @InjectMocks
  private UserService userService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordHistoryRepository passwordHistoryRepository;

  private User testUser;

  @BeforeEach
  public void setUpTestUser() {
    testUser = User.builder()
        .userId("test12345")
        .userName("테스트유저")
        .userPassword("@test12345")
        .userEmail("test@gmail.com")
        .description("한줄소개")
        .authority(UserRoleEnum.USER)
        .build();
    userRepository.save(testUser);
  }

  @Test
  @DisplayName("프로필 조회")
  void getUser() {
    // given
    given(userRepository.findById(anyLong())).willReturn(Optional.of(testUser));

    // when
    UserResponseDto responseDto = userService.getUser(1L);

    //then
    assertEquals(testUser.getUserId(), responseDto.getUserId());
  }

  @Test
  @DisplayName("프로필 수정")
  void updateUser() {
    // given
    Long userSeq = 1L;
    given(userRepository.findById(userSeq)).willReturn(Optional.of(testUser));

    UserRequestDto requestDto = Mockito.mock(UserRequestDto.class);
    given(requestDto.getUserName()).willReturn("username변경");
    given(requestDto.getDescription()).willReturn("description변경");

    // when
    UserResponseDto responseDto = userService.updateUser(requestDto, userSeq);

    // then
    assertEquals("username변경", responseDto.getUserName());
    assertEquals("description변경", responseDto.getDescription());
  }

  @Test
  @DisplayName("비밀번호 변경 성공")
  void updatePassword_Success() {
    // given
    Long userSeq = 1L;
    given(userRepository.findById(userSeq)).willReturn(Optional.of(testUser));

    UserPwRequestDto requestDto = Mockito.mock(UserPwRequestDto.class);
    given(requestDto.getPassword()).willReturn("@test12345");
    given(requestDto.getNewPassword()).willReturn("@password1234");

    given(passwordEncoder.matches(requestDto.getPassword(),testUser.getUserPassword())).willReturn(true);
    given(passwordEncoder.matches(requestDto.getNewPassword(), testUser.getUserPassword())).willReturn(false);
    given(passwordHistoryRepository.findTop3ByUserOrderByChangedAtDesc(testUser)).willReturn(new ArrayList<>());

    // when
    userService.updatePassword(requestDto, userSeq);

    // then
    verify(passwordHistoryRepository).save(any(PasswordHistory.class));
    verify(userRepository).save(testUser);
  }

  @Test
  @DisplayName("현재 비밀번호 불일치")
  void updatePassword_Fail_NotMatchCurrentPassword() {
    // given
    Long userSeq = 1L;
    given(userRepository.findById(userSeq)).willReturn(Optional.of(testUser));

    UserPwRequestDto requestDto = Mockito.mock(UserPwRequestDto.class);
    given(requestDto.getPassword()).willReturn("@test12345");

    given(passwordEncoder.matches(requestDto.getPassword(),testUser.getUserPassword())).willReturn(false);

    // when-then
    CustomException exception = assertThrows(CustomException.class,
        () -> userService.updatePassword(requestDto, userSeq));

    assertEquals(ErrorCode.CURRENT_PASSWORD_MATCH, exception.getErrorCode());
  }

  @Test
  @DisplayName("변경하려는 비밀번호와 동일한 비밀번호")
  void updatePassword_Fail_SamePassword() {
    // given
    Long userSeq = 1L;
    given(userRepository.findById(userSeq)).willReturn(Optional.of(testUser));

    UserPwRequestDto requestDto = Mockito.mock(UserPwRequestDto.class);
    given(requestDto.getPassword()).willReturn("@test12345");
    given(requestDto.getNewPassword()).willReturn("@password12345");

    // when
    given(passwordEncoder.matches(requestDto.getPassword(),testUser.getUserPassword())).willReturn(true);
    given(passwordEncoder.matches(requestDto.getNewPassword(),testUser.getUserPassword())).willReturn(true);

    // then
    CustomException exception = assertThrows(CustomException.class,
        () -> userService.updatePassword(requestDto, userSeq));

    assertEquals(ErrorCode.SAME_NEW_PASSWORD, exception.getErrorCode());
  }

  @Test
  @DisplayName("최근에 사용한 비밀번호와 동일한 경우")
  void updatePassword_Fail_RecentSamePassword() {
    // given
    Long userSeq = 1L;
    given(userRepository.findById(userSeq)).willReturn(Optional.of(testUser));

    UserPwRequestDto requestDto = Mockito.mock(UserPwRequestDto.class);
    given(requestDto.getPassword()).willReturn("@test12345");
    given(requestDto.getNewPassword()).willReturn("@password12345");

    PasswordHistory recentPasswords = new PasswordHistory(testUser,"@password12345", LocalDateTime.now());
    List<PasswordHistory> usedPasswords = new ArrayList<>();
    usedPasswords.add(recentPasswords);

    given(passwordEncoder.matches(requestDto.getPassword(), testUser.getUserPassword())).willReturn(true);
    given(passwordEncoder.matches(requestDto.getNewPassword(),testUser.getUserPassword())).willReturn(false);
    given(passwordHistoryRepository.findTop3ByUserOrderByChangedAtDesc(testUser)).willReturn(usedPasswords);
    given(passwordEncoder.matches(requestDto.getNewPassword(), usedPasswords.get(0).getPassword())).willReturn(true);

    // when-then
    CustomException exception = assertThrows(CustomException.class,
        ()-> userService.updatePassword(requestDto, userSeq));

    assertEquals(ErrorCode.RECENT_PASSWORD_MATCH, exception.getErrorCode());
  }
}