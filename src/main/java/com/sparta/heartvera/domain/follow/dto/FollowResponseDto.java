package com.sparta.heartvera.domain.follow.dto;

import com.sparta.heartvera.domain.user.entity.User;
import lombok.Getter;

@Getter
public class FollowResponseDto {
  private String userName;

  public FollowResponseDto(User user) {
    this.userName = user.getUserName();
  }
}
