package com.hanghae.cloneinstagram.rest.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class LikePostUsersResponseDto {
    private String profileUrl;
    private String username;
    //private boolean follow;

    public LikePostUsersResponseDto(LikePostUserInterface likePostUser) {
        this.profileUrl = likePostUser.getProfile_url();
        this.username = likePostUser.getUsername();
        //this.follow = likePostUser.getFollow();
    }
}
