package com.sparta.heartvera.domain.user.dto;

import com.sparta.heartvera.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

  private String userId;
  private String userName;
  private String userEmail;
  private String description;
  private int likedPostsCount;
  private int likedCommentsCount;

  public UserResponseDto(User user, int likedPostsCount, int likedCommentsCount) {
    this.userId = user.getUserId();
    this.userName = user.getUserName();
    this.userEmail = user.getUserEmail();
    this.description = user.getDescription();
    this.likedPostsCount = likedPostsCount;
    this.likedCommentsCount = likedCommentsCount;
  }
}
