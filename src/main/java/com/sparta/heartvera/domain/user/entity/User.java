package com.sparta.heartvera.domain.user.entity;

import com.sparta.heartvera.common.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Table(name = "users")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Builder 사용시 필요한 AllArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long userSeq;

    @NotBlank
    @Column(name = "user_id")
    private String userId;

    @NotBlank
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name")
    private String userName;

    @Email
    @Column(name = "email")
    private String userEmail;

    @Column(name = "description")
    private String description;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum authority;

}
