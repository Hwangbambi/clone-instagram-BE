package com.hanghae.cloneinstagram.rest.post.dto;

import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String username;
    private String content;
    private String imgUrl;

    @Getter
    @NoArgsConstructor
    public static class saveResponse {
        private Long id;
        private String username;
        private String content;
        private String imgUrl;
        public saveResponse(Post post) {
            this.id = post.getId();
            this.username = post.getUsername();
            this.content = post.getContent();
            this.imgUrl = post.getImgUrl();
        }
    }
}
