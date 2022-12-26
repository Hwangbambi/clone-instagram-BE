package com.hanghae.cloneinstagram.rest.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HashtagRequestDto {
    private Long postId;
    private String hashtag;

}
