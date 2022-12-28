package com.hanghae.cloneinstagram.rest.follow.service;

import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.errorcode.UserStatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.follow.dto.FollowRecommendListDto;
import com.hanghae.cloneinstagram.rest.follow.dto.FollowRequestDto;
import com.hanghae.cloneinstagram.rest.follow.dto.FollowResponseDto;
import com.hanghae.cloneinstagram.rest.follow.model.Follow;
import com.hanghae.cloneinstagram.rest.follow.repository.FollowRepository;
import com.hanghae.cloneinstagram.rest.like.dto.LikePostUsersResponseDto;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    //회원 팔로우
    //팔로우 추천 목록 조회 - 내가 팔로우 하지 않는 유저 (게시글 많은 순서)
    public FollowRecommendListDto getFollows() {
        User user = SecurityUtil.getCurrentUser();

        FollowRecommendListDto followRecommendListDtos = new FollowRecommendListDto();

        //follow 테이블에서 user.getId() 로 조회한 결과, 나오는 followId 는 제외하고 리턴?, 게시글 많은 순으로 조회
        List<Follow> followList = followRepository.findByUserId(user.getId());



        return followRecommendListDtos;
    }

    public StatusCode following(Long followId) {
        User user = SecurityUtil.getCurrentUser();

        //followId 회원 탈퇴 유무 확인
        boolean followUser = userRepository.existsByIdAndDeletedIsFalse(followId);

        if (!followUser) {
            throw new RestApiException(UserStatusCode.DELETE_USER);
        }

        //팔로우 중복 클릭시 언팔로우
        Optional<Follow> follow = followRepository.findByUserIdAndFollowId(user.getId(), followId);

        if (!follow.isPresent()){
            followRepository.saveAndFlush(new Follow(new FollowRequestDto(user.getId(), followId)));
            return CommonStatusCode.FOLLOW_USER;
        } else {
            followRepository.delete(follow.get());
            return CommonStatusCode.UNFOLLOW_USER;
        }
    }
}
