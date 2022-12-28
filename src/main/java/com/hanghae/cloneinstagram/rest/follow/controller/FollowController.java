package com.hanghae.cloneinstagram.rest.follow.controller;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.rest.follow.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name="Follow", description = "팔로우 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "팔로우 추천 목록", description = "내가 팔로우 하지 않는 유저 목록")
    @GetMapping("/follows")
    public PrivateResponseBody getFollows() {
        return new PrivateResponseBody(CommonStatusCode.OK, followService.getFollows());
    }


    @Operation(summary = "팔로우", description = "회원 팔로우& 팔로우 취소하기")
    @PostMapping("/follows/{followId}")
    public PrivateResponseBody following(@PathVariable Long followId) {
        return followService.following(followId);
    }
    
}
