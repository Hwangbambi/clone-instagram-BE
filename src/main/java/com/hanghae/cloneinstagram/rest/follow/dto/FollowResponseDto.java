package com.hanghae.cloneinstagram.rest.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDto {
     public Long userId;
     public Long followId;
}
