package com.hanghae.cloneinstagram.rest.post.service;

import com.hanghae.cloneinstagram.config.S3.AwsS3Service;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import com.hanghae.cloneinstagram.rest.comment.repository.CommentRepository;
import com.hanghae.cloneinstagram.rest.post.dto.PostListResponseDto;
import com.hanghae.cloneinstagram.rest.post.dto.PostRequestDto;
import com.hanghae.cloneinstagram.rest.post.dto.PostResponseDto;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;

    public PostResponseDto.saveResponse savePost(PostRequestDto postRequestDto) {
        User user = SecurityUtil.getCurrentUser();

        String imgUrl = null;

        //첨부파일 존재할 때
        if (!postRequestDto.getFile().isEmpty() && !postRequestDto.getFile().getContentType().isEmpty()) {
            imgUrl = awsS3Service.uploadFile(postRequestDto.getFile());
        }

        Post post = postRepository.saveAndFlush(new Post(postRequestDto, imgUrl, user));

        return new PostResponseDto.saveResponse(post);

    }

    public StatusCode deletePost(Long postId) {
        User user = SecurityUtil.getCurrentUser();

        //조회되는 게시글 없을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
        );

        //작성자 불일치
        if (!postRepository.existsByIdAndUserId(postId, user.getId())) {
            throw new RestApiException(CommonStatusCode.INVALID_USER);
        }

        //첨부파일 있을 경우 파일 삭제 처리
        if (post.getImgUrl() != null) {
            String fileName = post.getImgUrl().split(".com/")[1];
            awsS3Service.deleteFile(fileName);
        }

        //게시글 삭제 - soft delete
        post.update();

        return CommonStatusCode.DELETE_POST;
    }

    public PostResponseDto.getOriginalPost getOriginalPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
        );

        return new PostResponseDto.getOriginalPost(post);
    }

    public PostListResponseDto getPosts() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();

        //작성일 기준 내림차순
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        for (Post post : postList) {
            postListResponseDto.addPostList(new PostResponseDto(post));
        }

        return postListResponseDto;
    }
}
