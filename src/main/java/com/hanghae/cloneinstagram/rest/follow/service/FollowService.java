package com.hanghae.cloneinstagram.rest.follow.service;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.UserStatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.follow.dto.FollowRecommendListDto;
import com.hanghae.cloneinstagram.rest.follow.dto.FollowResponseDto;
import com.hanghae.cloneinstagram.rest.follow.model.Follow;
import com.hanghae.cloneinstagram.rest.follow.repository.FollowRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
     private final UserRepository userRepository;
     private final FollowRepository followRepository;
     
     //회원 팔로우
     //팔로우 추천 목록 조회 - 내가 팔로우 하지 않는 유저 (게시글 많은 순서)
     @Transactional(readOnly = true)
     public FollowRecommendListDto getFollows() {
          // 현재 로그인한 유저
          User user = SecurityUtil.getCurrentUser();
          
          
          //follow 테이블에서 user.getId() 로 조회한 결과, 나오는 followId 는 제외하고 리턴?, 게시글 많은 순으로 조회
          List<FollowResponseDto.FollowRecomResponseDto> followList = followRepository.findByUserId(user.getId()).stream()
                                                  .map(FollowResponseDto.FollowRecomResponseDto::new)
                                                  .collect(Collectors.toList());
          
          return new FollowRecommendListDto(followList);
     }
     
     // 상대 userId로 팔로우 , 언팔로우
     @Transactional
     public PrivateResponseBody<FollowResponseDto> following(Long followId) {
          User user = SecurityUtil.getCurrentUser();
          
          //followId 회원 탈퇴 유무 확인
          boolean followUser = userRepository.existsByIdAndDeletedIsFalse(followId);
          if (!followUser) throw new RestApiException(UserStatusCode.NO_USER);
          
          // 기존에 팔로우한 사람인가?
          Follow originFollow = followRepository.findByUserIdAndFollowId(user.getId(), followId).orElse(null);
          
          if (originFollow == null) {// 기존 팔로우 아닌사람 : 팔로우 추가
               Follow follow = followRepository.saveAndFlush(new Follow(user.getId(), followId));
               return new PrivateResponseBody(CommonStatusCode.FOLLOW_USER, new FollowResponseDto(user.getId(), followId));
          } else {//기존에 팔로우 한사람 : 팔로우 취소
               followRepository.delete(originFollow);
               return new PrivateResponseBody(CommonStatusCode.UNFOLLOW_USER);
          }
     }
}
