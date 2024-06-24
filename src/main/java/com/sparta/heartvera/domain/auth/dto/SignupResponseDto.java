package com.sparta.heartvera.domain.auth.dto;

import com.sparta.heartvera.domain.user.entity.User;
import com.sparta.heartvera.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String userId;

    private UserRoleEnum authority;

    public SignupResponseDto(User user) {
        userId = user.getUserId();
        authority = user.getAuthority();
    }
}
