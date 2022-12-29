package com.hanghae.cloneinstagram.rest.post.dto;

import java.time.LocalDateTime;

public interface PostUsernameInterface {
     Long getId();
     String getProfile_url();
     Long getUser_id();
     String getContent();
     String getImg_url();
     String getUsername();
     int getLikes();
     LocalDateTime getCreated_at();
     LocalDateTime getModified_at();
     Long getIsLike();
}
