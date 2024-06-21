package com.sparta.heartvera.domain.follow.service;

import com.sparta.heartvera.common.exception.CustomException;
import com.sparta.heartvera.common.exception.ErrorCode;
import com.sparta.heartvera.domain.follow.dto.FollowResponseDto;
import com.sparta.heartvera.domain.follow.entity.Follow;
import com.sparta.heartvera.domain.follow.repository.FollowRepository;
import com.sparta.heartvera.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;

  @Transactional
  public void followUser(User toUser, User fromUser) {
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

  @Transactional
  public void deleteFollowUser(User toUser, User fromUser) {
    Optional<Follow> checkFollow = followRepository.findByFromUserAndToUser(fromUser, toUser);
    if(checkFollow.isEmpty()) {
      throw new CustomException(ErrorCode.RECENT_NOT_FOLLOW);
    }
    followRepository.delete(checkFollow.get());
  }

  @Transactional(readOnly = true)
  public List<FollowResponseDto> getFollowings(User user) {
    List<FollowResponseDto> followResponseDtoList = new ArrayList<>();
    for(Follow follow : user.getFollowings()) {
      followResponseDtoList.add(new FollowResponseDto(follow.getToUser()));
    }
    return followResponseDtoList;
  }
}
