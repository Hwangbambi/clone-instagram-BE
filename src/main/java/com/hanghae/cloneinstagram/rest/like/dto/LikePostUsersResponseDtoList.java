package com.hanghae.cloneinstagram.rest.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class LikePostUsersResponseDtoList {
    
    public List<LikePostUsersResponseDto> userList = new ArrayList<>();
    
    
    @Getter
    @NoArgsConstructor
    public static class LikePostUsersResponseDto{
        private String profileUrl;
        private Long userId;
        private String username;
        private boolean follow;
    
        public LikePostUsersResponseDto(LikePostUserInterface likePostUser) {
            this.profileUrl = likePostUser.getProfile_url();
            this.userId = likePostUser.getUser_id();
            this.username = likePostUser.getUsername();
            this.follow = likePostUser.getFollow() != null;
        }
        
    }
}
