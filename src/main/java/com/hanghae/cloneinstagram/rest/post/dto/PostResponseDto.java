package com.hanghae.cloneinstagram.rest.post.dto;

import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String content;
    private String imgUrl;

    @Getter
    @NoArgsConstructor
    public static class saveResponse {
        private Long id;
        private Long userId;
        private String content;
        private String imgUrl;
        public saveResponse(Post post) {
            this.id = post.getId();
            this.userId = post.getUserId();
            this.content = post.getContent();
            this.imgUrl = post.getImgUrl();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class getOriginalPost {
        private Long id;
        private String content;
        private String imgUrl;
        public getOriginalPost(Post post) {
            this.id = post.getId();
            this.content = post.getContent();
            this.imgUrl = post.getImgUrl();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class getPosts {

        public getPosts(Post post) {

        }
    }
}
