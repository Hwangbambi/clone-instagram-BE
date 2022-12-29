package com.hanghae.cloneinstagram.rest.like.service;

import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import com.hanghae.cloneinstagram.rest.comment.repository.CommentRepository;
import com.hanghae.cloneinstagram.rest.like.dto.LikePostUsersResponseDtoList;
import com.hanghae.cloneinstagram.rest.like.model.CommentLike;
import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import com.hanghae.cloneinstagram.rest.like.repository.LikeCommentRepository;
import com.hanghae.cloneinstagram.rest.like.repository.LikePostRepository;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
     private final CommentRepository commentRepository;
     
     private final PostRepository postRepository;
     private final LikePostRepository likePostRepository;
     private final LikeCommentRepository likeCommentRepository;
     
     // 게시글 좋아요
     @Transactional
     public StatusCode PostLike(Long postId) {
          User user = SecurityUtil.getCurrentUser();
          Post post = postRepository.findById(postId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          
          // 로그인한 유저의 아이디, 게시글의 아이디 로 검색
          PostLike postLike = likePostRepository.findByUserIdAndPostId(user.getId(), post.getId()).orElseGet(new PostLike());
          if (postLike != null) { // 좋아요 눌러져있을때
               post.unLike(); // 게시글 좋아요수 --
               likePostRepository.delete(postLike); // 좋아요 테이블에서 삭제
               return CommonStatusCode.POST_LIKE_CANCEL;
          } else {
               PostLike newPostLike = new PostLike(post.getId(), user.getId());
               post.addLike(); // 게시글 좋아요수 ++
               likePostRepository.save(newPostLike);
               return CommonStatusCode.POST_LIKE;
          }
     }
     
     // 댓글 좋아요
     @Transactional
     public StatusCode CommentLike(Long commentId) {
          User user = SecurityUtil.getCurrentUser();
          Comment comment = commentRepository.findById(commentId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_COMMENT)
          );
          // 로그인한 유저의 아이디, 코멘트 아이디 로 검색
          CommentLike commentLike = likeCommentRepository.findByUserIdAndCommentId(user.getId(), comment.getId()).orElseGet(new CommentLike());
          if (commentLike != null) { // 이미 좋아요 누른상태
               comment.unLike();
               likeCommentRepository.delete(commentLike);
               return CommonStatusCode.COMMENT_LIKE_CANCEL;
          } else {
               CommentLike newCommentLike = new CommentLike(comment.getId(), user.getId());
               comment.addLike();
               likeCommentRepository.save(newCommentLike);
               return CommonStatusCode.COMMENT_LIKE;
          }
     }
     
     // 해당 게시글 좋아요 누른 사람들 리스트
     @Transactional (readOnly = true)
     public LikePostUsersResponseDtoList getPostLikes(Long postId) {
          User loggedUser = SecurityUtil.getCurrentUser();
          // 리스트를 보내주기
          // 해당게시글의 좋아요리스트 (게시글 삭제시 관련좋아요는 delete됨)
          // 내가 팔로우 한지 안한지 도 보내주기. isFollow : true, false
          // 팔로우 한사람이 위쪽
          List<LikePostUsersResponseDtoList.LikePostUsersResponseDto> likePostUsersResponseDtos = likePostRepository.findByPostId(postId, loggedUser.getId())
                                        .stream()
                                        .map(LikePostUsersResponseDtoList.LikePostUsersResponseDto::new)
                                        .collect(Collectors.toList());
          
          return new LikePostUsersResponseDtoList(likePostUsersResponseDtos);
     }
     
}
