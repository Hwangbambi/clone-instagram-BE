package com.hanghae.cloneinstagram.rest.like.service;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import com.hanghae.cloneinstagram.rest.like.repository.LikeRepository;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
     
     private final PostRepository postRepository;
     private final LikeRepository likeRepository;
     
     @Transactional
     public StatusCode PostLike(Long postId) {
          User user = SecurityUtil.getCurrentUser();
          Post post = postRepository.findById(postId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          
          // 로그인한 유저의 아이디, 게시글의 아이디 로 검색
          PostLike postLike = likeRepository.findByUserIdAndPostId(user.getId(), post.getId()).orElseGet(new PostLike());
          if(postLike != null){ // 좋아요 눌러져있을때
               post.unLike(); // 게시글 좋아요수 --
               likeRepository.delete(postLike); // 좋아요 테이블에서 삭제
               return CommonStatusCode.POST_LIKE_CANCEL;
          }else{
               PostLike newPostLike = new PostLike(post.getId(), user.getId());
               post.addLike(); // 게시글 좋아요수 ++
               likeRepository.save(newPostLike);
               return CommonStatusCode.POST_LIKE;
          }
     }
     
     // 해당 게시글 좋아요 누른 사람들 리스트
     @Transactional(readOnly = true)
     public PrivateResponseBody getPostLikes(Long postId) {
          // 내가 팔로우 한지 안한지 도 보내주기. isFollow true, false,
          // 팔로우 한사람이 위쪽
          // 리스트를 보내주기
          return null;
     }
}
