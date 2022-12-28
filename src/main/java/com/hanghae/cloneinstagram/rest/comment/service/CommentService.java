package com.hanghae.cloneinstagram.rest.comment.service;

import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.comment.dto.CommentRequestDto;
import com.hanghae.cloneinstagram.rest.comment.dto.CommentResponseDto;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import com.hanghae.cloneinstagram.rest.comment.repository.CommentRepository;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
     private final CommentRepository commentRepository;
     private final PostRepository postRepository;
     private final UserRepository userRepository;
     
     @Transactional (readOnly = true)
     public List<Comment> getCommentList(Long postId) {
          //작성일 기준 내림차순, deleted is false
          List<Comment> commentList = commentRepository.findByPostIdAndDeletedIsFalseOrderByCreatedAtDesc(postId);
          return commentList;
     }
     
     @Transactional
     public CommentResponseDto.simpleCommentResponseDto saveComment(Long postId, CommentRequestDto requestDto) {
          // 토큰으로 유저(작성자) 가져오기
          User user = SecurityUtil.getCurrentUser();
          // postId 로 게시글 존재유무, 삭제유무
          Post post = postRepository.findByIdAndDeletedIsFalse(postId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          // 댓글 생성
          Comment comment = new Comment().builder()
               .userId(user.getId())
               .postId(post.getId())
               .content(requestDto.getContent())
               .deleted(false)
               .likes(0)
               .build();
          // 댓글 저장
          comment = commentRepository.save(comment);
          return new CommentResponseDto.simpleCommentResponseDto(comment, user.getUsername());
     }
     
     @Transactional
     public StatusCode deleteComment(Long commentId) {
          // 토큰으로 유저(작성자) 가져오기
          User user = SecurityUtil.getCurrentUser();
          // 댓글id 작성자와 로그인유저 일치 확인, 댓글 존재여부도 확인
          Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_COMMENT)
          );
          // 댓글 삭제
          commentRepository.delete(comment);
          return CommonStatusCode.DELETE_COMMENT;
     }
}
