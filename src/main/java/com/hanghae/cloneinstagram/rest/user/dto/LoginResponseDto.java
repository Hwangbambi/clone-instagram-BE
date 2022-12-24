package com.hanghae.cloneinstagram.rest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
     private String username;
     private String profileUrl;
}
