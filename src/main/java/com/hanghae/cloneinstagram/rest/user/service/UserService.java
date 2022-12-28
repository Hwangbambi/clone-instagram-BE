package com.hanghae.cloneinstagram.rest.user.service;

import com.hanghae.cloneinstagram.config.S3.AwsS3Service;
import com.hanghae.cloneinstagram.config.errorcode.UserStatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.jwt.JwtUtil;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.user.dto.*;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
     private final UserRepository userRepository;
     
     private final JwtUtil jwtUtil;
     
     private final PasswordEncoder passwordEncoder;
     private final AwsS3Service awsS3Service;
     
     @Transactional
     public void signUp(SignupRequestDto requestDto) {
          //username 중복확인
          if (userRepository.existsByUsername(requestDto.getUsername())) {
               throw new RestApiException(UserStatusCode.OVERLAPPED_USERNAME);
          }
          ;
          // email 중복확인
          if (userRepository.existsByEmail(requestDto.getEmail())) {
               throw new RestApiException(UserStatusCode.OVERLAPPED_EMAIL);
          }
          ;
          // 패스워드 암호화
          String password = passwordEncoder.encode(requestDto.getPassword());
          // 회원가입
          userRepository.save(new User(requestDto, password));
     }
     
     @Transactional
     public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
          // 사용자 확인
          User user = userRepository.findByEmailAndKakaoIdIsNull(loginRequestDto.getEmail()).orElseThrow(
               () -> new RestApiException(UserStatusCode.NO_USER)
          );
          // 비밀번호 확인
          if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
               throw new RestApiException(UserStatusCode.WRONG_PASSWORD);
          }
          // 탈퇴여부 확인
          if (user.getDeleted()) throw new RestApiException(UserStatusCode.DELETE_USER);
          // header 에 토큰추가
          response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
          // return
          return new LoginResponseDto(user.getUsername(), user.getProfileUrl());
     }
     
     public String getProfileUrl(Long userId) {
          Optional<User> user = userRepository.findById(userId);
          String profileUrl = user.get().getProfileUrl();
          
          return profileUrl;
     }
     
     @Transactional
     // 프로필 이미지 변경
     public ProfileResponseDto updateProfile(ProfileRequestDto requestDto) {
          // 로그인 유저정보
          User loggedUser = SecurityUtil.getCurrentUser();
          User user = userRepository.findById(loggedUser.getId()).orElseThrow(
               () -> new RestApiException(UserStatusCode.NO_USER)
          );
          // 현재 프로필이미지 들고오기
          String currentImgUrl = user.getProfileUrl();
          // 받은 이미지가 empty일 경우 프로필 null로
          if (requestDto.getFile().isEmpty()) {
               if(user.getProfileUrl()!=null) {
                    // s3에서도 삭제
                    awsS3Service.deleteFile(user.getProfileUrl().split(".com/")[1]);
               }
               user.deleteImg();
               return new ProfileResponseDto(null);
          } else {
               // 받은 이미지가 있을경우  update
               // s3에 파일저장, get img url
               String newImgUrl = awsS3Service.uploadFile(requestDto.getFile());
               user.updateImg(newImgUrl);
               return new ProfileResponseDto(newImgUrl);
          }
     }

//     User user = SecurityUtil.getCurrentUser();// 비회원일경우 null

//     Post post = postRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
//          // 삭제 or 존재하지않는 글일경우
//          () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
//     );
//     throw new RestApiException(CommonStatusCode.INVALID_USER);
}
