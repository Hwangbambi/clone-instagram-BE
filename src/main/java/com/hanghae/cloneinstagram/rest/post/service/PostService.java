package com.hanghae.cloneinstagram.rest.post.service;

import com.hanghae.cloneinstagram.config.S3.AwsS3Service;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.comment.model.Comment;
import com.hanghae.cloneinstagram.rest.comment.service.CommentService;
import com.hanghae.cloneinstagram.rest.hashtag.service.HashtagService;
import com.hanghae.cloneinstagram.rest.post.dto.*;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final HashtagService hashtagService;
    private final CommentService commentService;
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    @Transactional(readOnly = true)
    public PostListResponseDto getPosts() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();

        //작성일 기준 내림차순, deleted is false
        List<Post> postList = postRepository.findByDeletedIsFalseOrderByCreatedAtDesc();

        for (Post post : postList) {
            //해당 글번호에 대한 댓글 목록 조회
            List<Comment> commentList = commentService.getCommentList(post.getId());

            //작성자 profileUrl 조회
            String profileUrl = userService.getProfileUrl(post.getUserId());

            postListResponseDto.addPostList(new PostResponseDto(post, commentList, profileUrl));
        }

        return postListResponseDto;
    }
    
    @Transactional(readOnly = true)
    public PostListResponseDto.totalResponseDto getPosts2(Pageable pageable, String search, int postIdx) {
        PostListResponseDto.totalResponseDto postListResponseDto = new PostListResponseDto.totalResponseDto();
        
        //작성일 기준 내림차순, deleted is false
//        List<Post> postList = postRepository.findByDeletedIsFalseOrderByCreatedAtDesc();
//        List<Post> postList = postRepository.findByDeletedIsFalseOrderByCreatedAtDesc(pageable.getPageSize(), postIdx);
        Slice<Post> postList = postRepository.findAllByDeletedIsFalseOrderByIdDesc(PageRequest.of(0, postIdx));
        postListResponseDto.setCurrentSize(postList.getNumberOfElements());
        log.info("postList.getNumberOfElements() : {}" ,postList.getNumberOfElements());
        for (Post post : postList) {
            //해당 글번호에 대한 댓글 목록 조회
            List<Comment> commentList = commentService.getCommentList(post.getId());
            
            //작성자 profileUrl 조회
            String profileUrl = userService.getProfileUrl(post.getUserId());
            
            postListResponseDto.addPostList(new PostResponseDto(post, commentList, profileUrl));
        }
        
        return postListResponseDto;
    }

    @Transactional
    public PostResponseDto.saveResponse savePost(PostRequestDto postRequestDto) {
        User user = SecurityUtil.getCurrentUser();

        String imgUrl = null;

        //첨부파일 존재할 때
        if (!postRequestDto.getFile().isEmpty() && !postRequestDto.getFile().getContentType().isEmpty()) {
            imgUrl = awsS3Service.uploadFile(postRequestDto.getFile());
        }

        Post post = postRepository.saveAndFlush(new Post(postRequestDto, imgUrl, user));

        boolean isHashtag = postRequestDto.getContent().contains("#");

        if (isHashtag) {
            //해시태그 저장
            hashtagService.saveHashtag(post.getId(), postRequestDto.getContent());
        }

        return new PostResponseDto.saveResponse(post);

    }

    @Transactional
    public StatusCode deletePost(Long postId) {
        User user = SecurityUtil.getCurrentUser();

        //조회되는 게시글 없을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
        );

        //삭제된 게시글이라면
        if (post.isDeleted()) {
            throw new RestApiException(CommonStatusCode.NO_ARTICLE);
        }

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

        //해시태그 삭제
        hashtagService.deleteHashtag(postId);

        return CommonStatusCode.DELETE_POST;
    }

    @Transactional(readOnly = true)
    public PostResponseDto.getOriginalPost getOriginalPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
        );

        return new PostResponseDto.getOriginalPost(post);
    }


    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
        );

        //삭제된 게시글이라면
        if (post.isDeleted()) {
            throw new RestApiException(CommonStatusCode.NO_ARTICLE);
        }

        List<Comment> commentList = commentService.getCommentList(postId);

        //작성자 profileUrl 조회
        String profileUrl = userService.getProfileUrl(post.getUserId());

        return new PostResponseDto(post, commentList, profileUrl);
    }
}
