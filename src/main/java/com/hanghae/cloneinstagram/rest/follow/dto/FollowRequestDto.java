package com.hanghae.cloneinstagram.rest.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowRequestDto {
    @Schema(description = "로그인한 유저 아이디")
    private Long userId;

    @Schema(description = "팔로우한 유저 아이디")
    private Long followId;
    public FollowRequestDto(Long id, Long followId) {
        this.userId = id;
        this.followId = followId;
    }
}
