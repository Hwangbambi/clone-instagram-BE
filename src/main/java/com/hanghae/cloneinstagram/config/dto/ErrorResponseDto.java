package com.hanghae.cloneinstagram.config.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
     private boolean success = false;
     private final String statusMsg;
     private final int statusCode;
     
     public ErrorResponseDto(String statusMsg, int statusCode){
          this.statusMsg = statusMsg;
          this.statusCode = statusCode;
     }
}
