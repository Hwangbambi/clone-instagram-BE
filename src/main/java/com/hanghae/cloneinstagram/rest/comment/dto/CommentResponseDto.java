package com.hanghae.cloneinstagram.rest.comment.dto;

import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
     @Schema (description = "댓글 아이디")
     private Long id;
     
     @Schema(description = "댓글작성 유저명")
     private String username;
     
     @Schema(description = "댓글작성 유저 프로필url")
     private String profileUrl;
     
     @Schema(description = "댓글 내용")
     private String content;
     
     @Schema(description = "로그인 유저의 댓글 좋아요 유무")
     private Boolean like;
     
     @Schema(description = "댓글 생성 날짜")
     private LocalDateTime createdAt;
     
     public CommentResponseDto(Comment comment, String username) {
          this.id = comment.getId();
          this.username = username;
          this.content = comment.getContent();
     }
     
     public CommentResponseDto(CommentUsernameInterface commentUsernameInterface) {
          this.id = commentUsernameInterface.getId();
          this.username = commentUsernameInterface.getUsername();
          this.profileUrl = commentUsernameInterface.getProfile_url();
          this.content = commentUsernameInterface.getContent();
          this.like = false; // 수정필요
          this.createdAt = commentUsernameInterface.getCreated_at();
     }
     
     @Getter
     @NoArgsConstructor
     public static class simpleCommentResponseDto {
          @Schema (description = "댓글 아이디")
          private Long id;
     
          @Schema(description = "댓글작성 유저명")
          private String username;
     
          @Schema(description = "댓글 내용")
          private String content;
     
          public simpleCommentResponseDto(Comment comment, String username) {
               this.id = comment.getId();
               this.username = username;
               this.content = comment.getContent();
          }
     }
}
