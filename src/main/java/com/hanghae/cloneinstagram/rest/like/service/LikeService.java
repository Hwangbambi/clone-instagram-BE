package com.hanghae.cloneinstagram.rest.like.service;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.like.dto.LikePostUserInterface;
import com.hanghae.cloneinstagram.rest.like.dto.LikePostUsersResponseDto;
import com.hanghae.cloneinstagram.rest.like.model.PostLike;
import com.hanghae.cloneinstagram.rest.like.repository.LikeRepository;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
     
     private final PostRepository postRepository;
     private final LikeRepository likeRepository;
     
     @Transactional
     public PrivateResponseBody PostLike(Long postId) {
          User user = SecurityUtil.getCurrentUser();
          Post post = postRepository.findById(postId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
     
//          Likes like = likeRepository.findByUserIdAndPostId(user.getId(), post.getId()).orElseGet(new Likes());
//          if(like == null){//좋아요최초로누를때
//               post.like();
//               Likes likes = new Likes(user, post);
//               likeRepository.save(likes);
//               return new ResponseEntity<>(new PrivateResponseBody(CommonStatusCode.POST_LIKE, new LikeResponseDto(likes.isLike(), post.getLikes())), HttpStatus.OK);
//          } else {
//               if(like.isLike()){//좋아요눌려있을때취소
//                    post.likeCancel();
//                    likeRepository.deleteById(like.getId());
//                    return new ResponseEntity<>(new PrivateResponseBody(CommonStatusCode.POST_LIKE_CANCEL, new LikeResponseDto(false, post.getLikes())), HttpStatus.OK);
//               } else{//안눌려있을때다시좋아요
//                    post.like();
//                    Likes likes = new Likes(user, post);
//                    likeRepository.save(likes);
//                    return new ResponseEntity<>(new PrivateResponseBody(CommonStatusCode.POST_LIKE, new LikeResponseDto(likes.isLike(), post.getLikes())), HttpStatus.OK);
//               }
//          }
          return null;
     }
     
     // 해당 게시글 좋아요 누른 사람들 리스트
     @Transactional(readOnly = true)
     public List<LikePostUsersResponseDto> getPostLikes(Long postId) {
          // 내가 팔로우 한지 안한지 도 보내주기. isFollow : true, false
          // 팔로우 한사람이 위쪽
          // 리스트를 보내주기
          List<LikePostUsersResponseDto> likePostUsersResponseDtos = new ArrayList<>();

          List<LikePostUserInterface> likePostUserInterfaces = likeRepository.findByPostId(postId);

          for (LikePostUserInterface likePostUser : likePostUserInterfaces) {
               //좋아요 누른 유저 팔로우 유무 확인 - follow 구현 후 추후 추가


               likePostUsersResponseDtos.add(new LikePostUsersResponseDto(likePostUser));
          }
          return likePostUsersResponseDtos;
     }
}
