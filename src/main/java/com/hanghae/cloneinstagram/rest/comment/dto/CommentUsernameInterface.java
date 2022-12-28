package com.hanghae.cloneinstagram.rest.comment.dto;

import java.time.LocalDateTime;

public interface CommentUsernameInterface {
     Long getId();
     String getProfile_url();
     String getContent();
     String getUsername();
     LocalDateTime getCreated_at();
     Long getIsLike(); // 로그인한 유저가 해당댓글 좋아요 했는지 유무 있으면좋아요한거
}
