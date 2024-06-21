package com.sparta.heartvera.domain.follow.controller;

import com.sparta.heartvera.domain.follow.service.FollowService;
import com.sparta.heartvera.domain.user.entity.User;
import com.sparta.heartvera.domain.user.service.UserService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FolloweController {
  private final UserService userService;
  private final FollowService followService;

  // 팔로우
  @PostMapping("/follow/{userSeq}")
  public ResponseEntity<String> followUser(@PathVariable Long userSeq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    User fromUser = userService.findByUserSeq(userDetails.getUser().getUserSeq());
    User toUser = userService.findByUserSeq(userSeq);
    followService.followUser(fromUser, toUser);
    return ResponseEntity.status(HttpStatus.OK).body(toUser.getUserId() + " 님을 팔로우 하였습니다.");
  }

  // 언팔로우
  @DeleteMapping("/follow/{userSeq}")
 public ResponseEntity<String> deleteFollowUser(@PathVariable Long userSeq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    User fromUser = userService.findByUserSeq(userDetails.getUser().getUserSeq());
    User toUser = userService.findByUserSeq(userSeq);
    followService.deleteFollowUser(toUser);
    return ResponseEntity.status(HttpStatus.OK).body(toUser.getUserId() + " 님을 팔로우취소 하였습니다.");
  }
}
