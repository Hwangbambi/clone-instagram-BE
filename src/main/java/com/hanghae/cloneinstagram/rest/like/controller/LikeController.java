package com.hanghae.cloneinstagram.rest.like.controller;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.rest.like.service.LikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
public class LikeController {
     private final LikeService likeService;
     
     @ApiOperation (value = "게시글 좋아요")
     @PostMapping ("/like/{postId}")
     public PrivateResponseBody PostLike(@PathVariable Long postId){
          return likeService.PostLike(postId);
     }
     
     @ApiOperation (value = "게시글 좋아요한 사람들")
     @GetMapping ("/like/{postId}")
     public PrivateResponseBody getPostLikes(@PathVariable Long postId){
          return new PrivateResponseBody(CommonStatusCode.OK,likeService.getPostLikes(postId));
     }

}
