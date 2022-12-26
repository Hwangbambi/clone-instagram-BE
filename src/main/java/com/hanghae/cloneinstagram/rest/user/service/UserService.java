package com.hanghae.cloneinstagram.rest.user.service;

import com.hanghae.cloneinstagram.config.errorcode.UserStatusCode;
import com.hanghae.cloneinstagram.config.exception.RestApiException;
import com.hanghae.cloneinstagram.config.jwt.JwtUtil;
import com.hanghae.cloneinstagram.config.util.SecurityUtil;
import com.hanghae.cloneinstagram.rest.user.dto.LoginRequestDto;
import com.hanghae.cloneinstagram.rest.user.dto.LoginResponseDto;
import com.hanghae.cloneinstagram.rest.user.dto.SignupRequestDto;
import com.hanghae.cloneinstagram.rest.user.model.User;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
     private final UserRepository userRepository;
     
     private final JwtUtil jwtUtil;
     
     private final PasswordEncoder passwordEncoder;
     
     public void signUp(SignupRequestDto requestDto) {
          //username 중복확인
          if(userRepository.existsByUsername(requestDto.getUsername())){
               throw new RestApiException(UserStatusCode.OVERLAPPED_USERNAME);
          };
          // email 중복확인
          if(userRepository.existsByEmail(requestDto.getEmail())){
               throw new RestApiException(UserStatusCode.OVERLAPPED_EMAIL);
          };
          // 패스워드 암호화
          String password = passwordEncoder.encode(requestDto.getPassword());
          // 회원가입
          userRepository.save(new User(requestDto, password));
     }
     
     public Object login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
          // 사용자 확인
          User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
               () -> new RestApiException(UserStatusCode.NO_USER)
          );
          // 비밀번호 확인
          if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
               throw  new RestApiException(UserStatusCode.WRONG_PASSWORD);
          }
          // 탈퇴여부 확인
          if(user.getDeleted()) throw new RestApiException(UserStatusCode.DELETE_USER);
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

//     User user = SecurityUtil.getCurrentUser();// 비회원일경우 null

//     Post post = postRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
//          // 삭제 or 존재하지않는 글일경우
//          () -> new RestApiException(CommonStatusCode.NO_ARTICLE)
//     );
//     throw new RestApiException(CommonStatusCode.INVALID_USER);
}
