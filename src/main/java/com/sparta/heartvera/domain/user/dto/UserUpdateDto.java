package com.sparta.heartvera.domain.user.dto;

import com.sparta.heartvera.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateDto {
  @NotBlank(message = "이름을 입력해주세요")
  private String userName;

  @NotBlank(message = "한줄 소개 내용을 입력해주세요.")
  private String description;

  public UserUpdateDto(User user){
    this.userName = user.getUserName();
    this.description = user.getDescription();
  }

}
