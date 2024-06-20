package com.sparta.heartvera.domain.auth.dto;


import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userId;
    private String password;
}
