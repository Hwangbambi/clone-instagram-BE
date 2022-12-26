package com.hanghae.cloneinstagram.rest.post.dto;

import com.hanghae.cloneinstagram.rest.comment.dto.CommentResponseDto;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String profileUrl;
    private Long userId;
    private String content;
    private String imgUrl;
    private int likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Comment> commentList = new ArrayList<>();

    public PostResponseDto(Post post, List<Comment> commentList, String profileUrl) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.profileUrl = profileUrl;
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.imgUrl = post.getImgUrl();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = commentList;
    }

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
}
