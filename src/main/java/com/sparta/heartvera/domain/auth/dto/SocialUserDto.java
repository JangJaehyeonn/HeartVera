package com.sparta.heartvera.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialUserDto {
    private Long id;
    private String nickname;
    private String userId;
    private String password;
    private String email;
    private String description;
    private String authority;

    public SocialUserDto(String email, String nickname) {
        this.nickname = nickname;
        this.email = email;
    }
}