package com.hanghae.cloneinstagram.rest.user.service;

import com.hanghae.cloneinstagram.config.jwt.JwtUtil;
import com.hanghae.cloneinstagram.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
     private final UserRepository userRepository;
     
     private final JwtUtil jwtUtil;
     
     private final PasswordEncoder passwordEncoder;
//     User user = SecurityUtil.getCurrentUser();// 비회원일경우 null
}
