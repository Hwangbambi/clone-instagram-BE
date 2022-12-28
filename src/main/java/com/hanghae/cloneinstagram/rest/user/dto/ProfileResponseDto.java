package com.hanghae.cloneinstagram.rest.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfileResponseDto {
     
     @Schema(description = "프로필 이미지 url")
     private String profileUrl;
}
