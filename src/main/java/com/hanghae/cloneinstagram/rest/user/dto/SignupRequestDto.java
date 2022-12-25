package com.hanghae.cloneinstagram.rest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @Size(min = 5, max = 10)
    @Pattern (regexp="^(?=.*?[0-9])(?=.*?[a-z]).{5,10}$")
    @Schema(description = "유저명")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern (regexp="^.(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
    @Schema(description = "비밀번호")
    private String password;
    
    @NotBlank
    @Schema(description = "이메일주소")
    private String email;
    
    //@Schema(description = "타입", defaultValue = "BASIC", allowableValues = {"BASIC", "OWNER"})
}
