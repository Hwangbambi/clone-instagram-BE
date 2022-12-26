package com.hanghae.cloneinstagram.rest.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.StatusCode;
import com.hanghae.cloneinstagram.config.errorcode.UserStatusCode;
import com.hanghae.cloneinstagram.config.jwt.JwtUtil;
import com.hanghae.cloneinstagram.rest.user.dto.LoginRequestDto;
import com.hanghae.cloneinstagram.rest.user.dto.LoginResponseDto;
import com.hanghae.cloneinstagram.rest.user.dto.SignupRequestDto;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import com.hanghae.cloneinstagram.rest.user.service.KakaoService;
import com.hanghae.cloneinstagram.rest.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name="user", description = "사용자 API")
@RestController
@RequestMapping ("/api/user")
@RequiredArgsConstructor
public class UserController {
     
     private final PasswordEncoder passwordEncoder;
     private final UserRepository userRepository;
     private final UserService userService;
     private final KakaoService kakaoService;
     
     @Operation(summary = "회원가입", description = "email, username, password 로 회원가입 ")
     @ApiResponses(value={
          @ApiResponse(responseCode = "200", description = "회원가입 성공"),
          @ApiResponse(responseCode = "400", description = "중복된 username 입니다. 외 2개")
     })
     @PostMapping ("/signup")
     public PrivateResponseBody signup(@RequestBody @Valid SignupRequestDto requestDto) {
          userService.signUp(requestDto);
          return new PrivateResponseBody(UserStatusCode.USER_SIGNUP_SUCCESS);
     }
     
     @Operation(summary = "로그인", description = "email, password 로 로그인")
     @PostMapping ("/login")
     public PrivateResponseBody<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
          return new PrivateResponseBody(UserStatusCode.USER_LOGIN_SUCCESS, userService.login(loginRequestDto, response));
     }
     
     //https://kauth.kakao.com/oauth/authorize?client_id=ced49bfdb65f5f152e2e43f12e88bd86&redirect_uri=https://sparta-hippo.shop/api/user/kakao/callback&response_type=code
     //https://kauth.kakao.com/oauth/authorize?client_id=ced49bfdb65f5f152e2e43f12e88bd86&redirect_uri=http://localhost:8080/api/user/kakao/callback&response_type=code
     @Operation(summary = "카카오 로그인 콜백", description = "email, password 로 로그인")
     @GetMapping ("/kakao/callback")
     public PrivateResponseBody<LoginResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
          return new PrivateResponseBody(UserStatusCode.USER_LOGIN_SUCCESS, kakaoService.kakaoLogin(code, response));
     }
}
