package com.sparta.heartvera.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPwRequestDto {

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[!@#$%^*+=-]).{8,15}$",
            message = "비밀번호는 최소 8자 이상, 15자 이하이며 대소문자, 숫자, 특수문자(@#$%^&+=!)를 모두 포함해야 합니다."
    )
    private String password;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[!@#$%^*+=-]).{8,15}$",
            message = "비밀번호는 최소 8자 이상, 15자 이하이며 대소문자, 숫자, 특수문자(@#$%^&+=!)를 모두 포함해야 합니다."
    )
    private String newPassword;

}
