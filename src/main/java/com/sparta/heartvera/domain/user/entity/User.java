package com.sparta.heartvera.domain.user.entity;

import com.sparta.heartvera.common.Timestamped;
import com.sparta.heartvera.domain.comment.entity.Comment;
import com.sparta.heartvera.domain.follow.entity.Follow;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.user.dto.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PasswordHistory> passwordHistories;

  @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
  private List<Follow> followers;

  @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
  private List<Follow> followings;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Post> likedPosts;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Comment> likedComments;


  public void updateUser(UserRequestDto requestDto) {
    this.userName = requestDto.getUserName();
    this.description = requestDto.getDescription();
  }


  public void updatePassword(String newPassword) {
    this.userPassword = newPassword;
  }


  public void setRefreshToken(String token) {
        this.refreshToken = token;
    }

  public void setUserRole(UserRoleEnum role) { this.authority = role; }



}
