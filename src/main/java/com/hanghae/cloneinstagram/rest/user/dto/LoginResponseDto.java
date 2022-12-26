package com.hanghae.cloneinstagram.rest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
     @Schema (description = "유저명")
     private String username;
     
     @Schema(description = "비밀번호")
     private String profileUrl;
}
