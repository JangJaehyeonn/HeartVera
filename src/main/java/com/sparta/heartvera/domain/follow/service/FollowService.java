package com.sparta.heartvera.domain.follow.service;

import com.sparta.heartvera.common.exception.CustomException;
import com.sparta.heartvera.common.exception.ErrorCode;
import com.sparta.heartvera.domain.follow.entity.Follow;
import com.sparta.heartvera.domain.follow.repository.FollowRepository;
import com.sparta.heartvera.domain.user.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;

  public void followUser(User fromUser, User toUser) {
    if(fromUser.getUserSeq().equals(toUser.getUserSeq())) {
      throw new CustomException(ErrorCode.SAME_USER);
    }
    Optional<Follow> checkFollow = followRepository.findByFromUserAndToUser(fromUser, toUser);
    if(checkFollow.isPresent()) {
      throw new CustomException(ErrorCode.ALREADY_FOLLOW);
    }
    Follow follow = new Follow(fromUser, toUser);
    followRepository.save(follow);
  }

  public void deleteFollowUser(User fromUser, User toUser) {
    Optional<Follow> checkFollow = followRepository.findByFromUserAndToUser(fromUser, toUser);
    if(checkFollow.isEmpty()) {
      throw new CustomException(ErrorCode.RECENT_NOT_FOLLOW);
    }
    followRepository.delete(checkFollow.get());
  }


}
