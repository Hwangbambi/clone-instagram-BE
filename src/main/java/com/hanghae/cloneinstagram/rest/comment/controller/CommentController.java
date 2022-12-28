package com.hanghae.cloneinstagram.rest.comment.controller;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.rest.comment.dto.CommentRequestDto;
import com.hanghae.cloneinstagram.rest.comment.dto.CommentResponseDto;
import com.hanghae.cloneinstagram.rest.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag (name="Comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
     
     private final CommentService commentService;
     
     @Operation (summary = "댓글생성", description = "postId 에 댓글 생성")
     @PostMapping("/posts/{postId}/comments")
     public PrivateResponseBody<CommentResponseDto> saveComments(
          @PathVariable Long postId,
          @RequestBody CommentRequestDto requestDto){
          return new PrivateResponseBody(CommonStatusCode.OK, commentService.saveComment(postId, requestDto));
     }
     
     @Operation (summary = "댓글삭제", description = "commentId 댓글 삭제")
     @DeleteMapping("/comments/{commentId}")
     public PrivateResponseBody deleteComment(@PathVariable Long commentId){
          return new PrivateResponseBody(commentService.deleteComment(commentId));
     }
}
