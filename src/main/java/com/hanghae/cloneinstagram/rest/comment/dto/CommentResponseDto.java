package com.hanghae.cloneinstagram.rest.comment.dto;

import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
     @Schema (description = "댓글 아이디")
     private Long id;
     
     @Schema(description = "댓글작성 유저명")
     private String username;
     
     @Schema(description = "댓글 내용")
     private String content;
     
     public CommentResponseDto(Comment comment, String username) {
          this.id = comment.getId();
          this.username = username;
          this.content = comment.getContent();
     }
}
