package com.sparta.heartvera.domain.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequestDto {

  @NotNull(message = "팔로우하려는 유저의 아이디를 입력해주세요.")
  private Long toUserSeq;
}
