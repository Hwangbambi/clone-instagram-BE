package com.hanghae.cloneinstagram.rest.post.service;

import com.hanghae.cloneinstagram.config.S3.AwsS3Service;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.comment.dto.CommentResponseDto;
import com.hanghae.cloneinstagram.rest.comment.repository.CommentRepository;
import com.hanghae.cloneinstagram.rest.comment.service.CommentService;
import com.hanghae.cloneinstagram.rest.hashtag.service.HashtagService;
import com.hanghae.cloneinstagram.rest.post.dto.*;
import com.hanghae.cloneinstagram.rest.post.model.Post;
import com.hanghae.cloneinstagram.rest.post.repository.PostRepository;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
     private final CommentRepository commentRepository;
     private final PostRepository postRepository;
     private final HashtagService hashtagService;
     private final CommentService commentService;
     private final UserService userService;
     private final AwsS3Service awsS3Service;
     
     // 게시글 전체 불러오기 (팔로우 적용전임)
     @Transactional (readOnly = true)
     public PostListResponseDto.totalResponseDto getPosts(String search, int postIdx) {
          User user = SecurityUtil.getCurrentUser();
          PostListResponseDto.totalResponseDto postListResponseDto = new PostListResponseDto.totalResponseDto();
          List<PostUsernameInterface> postUsernameInterfaceList = new ArrayList<>();
          // search 가 username or hashtag
          if (search == null || search.equals("")) { // 검색 x
               //작성일 기준 내림차순, deleted is false
               postUsernameInterfaceList = postRepository.findAllByDeletedIsFalseAndByUserOrderByIdDesc(postIdx, user.getId());
          } else { // username으로 검색
               postUsernameInterfaceList = postRepository.findAllByUsernameAndDeletedIsFalseOrderByIdDesc(postIdx, search, user.getId());
          }
          List<PostResponseDto> postResponseDto = postUsernameInterfaceList.stream()
               .map(PostResponseDto::new)
               .map(postResponse -> {
                    Long postId = postResponse.getId();
                    // 전제게시글 조회시 댓글은 2개까지만 조회
                    List<CommentResponseDto> commentResponseDtoList = commentRepository.findByIdAndDeletedIsFalseOrderByCreatedAtDescLimit2(postId)
                         .stream().map(CommentResponseDto::new).collect(Collectors.toList());
                    postResponse.addCommentResponseDtos(commentResponseDtoList);
                    postResponse.setCommentsNum(commentResponseDtoList.size());
                    return postResponse;
               })
               .collect(Collectors.toList());
          
          postListResponseDto.setCurrentSize(postResponseDto.size());
          postListResponseDto.setPostList(postResponseDto);
          return postListResponseDto;
     }
     
     // 게시글 저장
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
     
     //게시글 삭제
     @Transactional
     public StatusCode deletePost(Long postId) {
          User user = SecurityUtil.getCurrentUser();

          //게시글 존재, delete is False
          Post post = postRepository.findByIdAndDeletedIsFalse(postId).orElseThrow(
                  () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          // 게시글 삭제시 게시글 좋아요테이블에도 삭제
          
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
     
     // 게시글 수정페이지 불러올때 게시글 간단정보 불러오기용
     @Transactional (readOnly = true)
     public PostResponseDto.getOriginalPost getOriginalPost(Long postId) {
          Post post = postRepository.findById(postId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          
          return new PostResponseDto.getOriginalPost(post);
     }
     
     // 게시글 상세조회 (팔로우 적용전)
     @Transactional (readOnly = true)
     public PostResponseDto getPost(Long postId) {
          // 게시글 아이디로 찾기 (게시글 삭제여부, 작성자 탈퇴여부 확인, username, profileUrl같이 들고오기)
          User user = SecurityUtil.getCurrentUser();
          PostUsernameInterface postInterface = postRepository.findByIdAndDeletedIsFalseAndByUserOrderByIdDesc(postId, user.getId()).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          PostResponseDto postResponseDto = new PostResponseDto(postInterface);
          
          List<CommentResponseDto> commentResponseDtoList = commentRepository.findByIdAndDeletedIsFalseOrderByCreatedAtDesc(postId)
                                                                 .stream().map(CommentResponseDto::new).collect(Collectors.toList());
          postResponseDto.addCommentResponseDtos(commentResponseDtoList);
          
          return postResponseDto;
     }
     
     // 게시글 수정
     @Transactional
     public StatusCode updatePost(Long postId, PostRequestDto postRequestDto) {
          User user = SecurityUtil.getCurrentUser();
          
          Post post = postRepository.findById(postId).orElseThrow(
               () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
          );
          
          //user 와 작성자 일치 여부 확인
          if (!user.getId().equals(post.getUserId())) {
               throw new RestApiException(CommonStatusCode.INVALID_USER);
          }
          
          String imageUrl = null;
          
          //기존 글에 첨부파일 존재시
          if (post.getImgUrl() != null) {
               if (!postRequestDto.getFile().isEmpty()) {
                    //첨부파일 수정시 기존 첨부파일 삭제
                    String fileName = post.getImgUrl().split(".com/")[1];
                    awsS3Service.deleteFile(fileName);
                    
                    //새로운 파일로 업로드
                    imageUrl = awsS3Service.uploadFile(postRequestDto.getFile());
                    
               } else {
                    //첨부파일 수정 안함
                    imageUrl = post.getImgUrl();
               }
          } else {
               //첨부파일 없는 글에
               if (!postRequestDto.getFile().isEmpty()) {
                    //첨부파일 적용
                    imageUrl = awsS3Service.uploadFile(postRequestDto.getFile());
               }
          }
          
          post.update(postRequestDto, imageUrl);
          
          return CommonStatusCode.UPDATE_POST;
     }
}
