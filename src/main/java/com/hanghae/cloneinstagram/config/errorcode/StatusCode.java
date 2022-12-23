package com.hanghae.cloneinstagram.config.errorcode;

public interface StatusCode {
     boolean isSuccess();
     String getStatusMsg();
     int getStatusCode();
}
