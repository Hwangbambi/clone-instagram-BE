package com.hanghae.cloneinstagram.rest.follow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FollowRecommendListDto {
    List<FollowResponseDto> followResponseDtos = new ArrayList<>();


}
