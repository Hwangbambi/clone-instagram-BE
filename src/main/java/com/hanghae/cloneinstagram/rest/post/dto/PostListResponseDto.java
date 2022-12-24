package com.hanghae.cloneinstagram.rest.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    List<PostResponseDto> postList = new ArrayList<>();
    public void addPostList(PostResponseDto.getPosts getPosts) {
    }
}
