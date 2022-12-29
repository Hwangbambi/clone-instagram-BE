package com.hanghae.cloneinstagram.rest.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowRecommendListDto {
    List<FollowResponseDto.FollowRecomResponseDto> followResponseList = new ArrayList<>();
}
