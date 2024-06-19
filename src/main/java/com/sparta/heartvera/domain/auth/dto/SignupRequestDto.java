package com.sparta.heartvera.domain.auth.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "아이디는 최소 4자 이상, 10자 이하의 알파벳 소문자와 숫자로 이루어져야 합니다.")
    private String userId;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z]).{8,15}$",
            message = "비밀번호는 최소 8자 이상, 15자 이하이며 대소문자, 숫자, 특수문자(@#$%^&+=!)를 모두 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 null이 들어올 수 없습니다.")
    private String userName;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    private String description;

    private String adminPassword;
}
