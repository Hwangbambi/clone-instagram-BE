package com.hanghae.cloneinstagram.rest.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDto {
     // 팔로우, 언팔로우 method용
     public Long userId;
     public Long followId;
     
     // 팔로우 추천 리스트용
     public static class FollowRecomResponseDto{
          public String profileUrl;
          public String username;
          public Long userId;
     
          public FollowRecomResponseDto(FollowRecomImpl followRecom) {
               this.profileUrl = followRecom.getProfile_url();
               this.username = followRecom.getUsername();
               this.userId = followRecom.getUserId();
          }
          
     }
}
