package com.hanghae.cloneinstagram.rest.post.controller;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.rest.post.dto.PostRequestDto;
import com.hanghae.cloneinstagram.rest.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시글 전체 조회")
    @GetMapping("/posts")
    public PrivateResponseBody getPosts(
         @RequestParam(value="size", defaultValue = "5") int size,
         @RequestParam(value="search", required = false) String search,
         @PageableDefault(size=5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
//        return new PrivateResponseBody(CommonStatusCode.OK, postService.getPosts());
        return new PrivateResponseBody(CommonStatusCode.OK, postService.getPosts2(pageable, search, size));
    }

    @ApiOperation(value = "게시글 상세 조회")
    @GetMapping("/posts/{postId}")
    public PrivateResponseBody getPost(@PathVariable Long postId) {

        return new PrivateResponseBody(CommonStatusCode.OK,postService.getPost(postId));
    }

    @ApiOperation(value = "게시글 작성 및 파일 업로드")
    @PostMapping("/posts")
    public PrivateResponseBody savePost(@ModelAttribute PostRequestDto postRequestDto) {
        return new PrivateResponseBody(CommonStatusCode.CREATE_POST,postService.savePost(postRequestDto));
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping ("/posts/{postId}")
    public PrivateResponseBody deletePost(@PathVariable Long postId) {
        return new PrivateResponseBody(postService.deletePost(postId));
    }

    @ApiOperation(value = "게시글 원본 조회-게시글 수정시 사용")
    @GetMapping("/posts/{postId}/update")
    public PrivateResponseBody getOriginalPost(@PathVariable Long postId) {
        return new PrivateResponseBody(CommonStatusCode.OK,postService.getOriginalPost(postId));
    }

    /*@ApiOperation(value = "게시글 수정")
    @PatchMapping("/posts/{postId}")*/










}
