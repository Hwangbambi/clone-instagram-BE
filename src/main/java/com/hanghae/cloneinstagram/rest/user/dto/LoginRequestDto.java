package com.hanghae.cloneinstagram.rest.user.dto;

import lombok.Getter;

@Getter
//@NoArgsConstructor
public class LoginRequestDto {
     private String email;
     private String password;
}
