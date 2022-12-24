package com.hanghae.cloneinstagram.rest.post.service;

import com.hanghae.cloneinstagram.config.S3.AwsS3Service;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.post.dto.PostRequestDto;
import com.hanghae.cloneinstagram.rest.post.dto.PostResponseDto;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
