package com.hanghae.cloneinstagram.rest.user.controller;

import com.hanghae.cloneinstagram.config.dto.PrivateResponseBody;
import com.hanghae.cloneinstagram.config.errorcode.CommonStatusCode;
import com.hanghae.cloneinstagram.config.errorcode.UserStatusCode;
import com.hanghae.cloneinstagram.rest.user.dto.SignupRequestDto;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import com.hanghae.cloneinstagram.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping ("/api/user")
@RequiredArgsConstructor
public class UserController {
     
     private final PasswordEncoder passwordEncoder;
     private final UserRepository userRepository;
     private final UserService userService;
     
     @PostMapping ("/signup")
     public ResponseEntity<PrivateResponseBody> signup(@RequestBody @Valid SignupRequestDto requestDto) {
          userService.signUp(requestDto);
//          return ResponseEntity.ok(new PrivateResponseBody(UserStatusCode.USER_SIGNUP_SUCCESS));
          return new ResponseEntity<>(new PrivateResponseBody(UserStatusCode.USER_SIGNUP_SUCCESS), HttpStatus.OK);
     }
     
//     @PostMapping ("/login")
//     public ResponseEntity<PrivateResponseBody> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
//          return new ResponseEntity<>(new PrivateResponseBody(UserStatusCode.USER_LOGIN_SUCCESS, userService.login(loginRequestDto, response)), HttpStatus.OK);
//     }
     
     
}
