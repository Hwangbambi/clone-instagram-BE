package com.hanghae.cloneinstagram.rest.comment.dto;

import java.time.LocalDateTime;

public interface CommentUsernameInterface {
     Long getId();
     String getProfile_url();
     String getContent();
     String getUsername();
     LocalDateTime getCreated_at();
}
