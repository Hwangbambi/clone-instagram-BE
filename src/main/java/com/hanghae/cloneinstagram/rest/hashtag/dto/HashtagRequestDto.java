package com.hanghae.cloneinstagram.rest.hashtag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HashtagRequestDto {
    private Long postId;
    private String hashtag;

    public HashtagRequestDto(Long id, String hashtag) {
        this.postId = id;
        this.hashtag = hashtag;
    }
}
