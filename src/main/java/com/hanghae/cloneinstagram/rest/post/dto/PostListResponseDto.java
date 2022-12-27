package com.hanghae.cloneinstagram.rest.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    List<PostResponseDto> postList = new ArrayList<>();
    public void addPostList(PostResponseDto postResponseDto) {
        postList.add(postResponseDto);
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class totalResponseDto{
        private int currentSize;
        private List<PostResponseDto> postList = new ArrayList<>();
    
        public void addResponseDto(PostResponseDto postResponseDto) {
            postList.add(postResponseDto);
        }
        
    }
}
