package com.hanghae.cloneinstagram.config.exception;

import com.hanghae.cloneinstagram.config.errorcode.StatusCode;

public class RestApiException extends RuntimeException{
     
     // 필드값
     private final StatusCode statusCode;
     
     //getter
     public StatusCode getStatusCode(){
          return this.statusCode;
     }
     // 생성자
     public RestApiException(StatusCode statusCode){
          this.statusCode = statusCode;
     }
}
